package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.actions.AbstractPaginationAction;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IProjectStatusService;
import cn.cstqb.exam.testmaker.services.ISyllabusService;
import cn.cstqb.exam.testmaker.services.IUserService;
import cn.cstqb.exam.testmaker.util.ServletUtils;
import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.inject.Inject;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/31
 * Time: 9:07
 */
public abstract class BaseProjectAction extends AbstractPaginationAction {
    @Inject protected IProjectService projectService;
    @Inject protected IProjectStatusService projectStatusService;
    @Inject protected IUserService userService;
    @Inject protected ISyllabusService syllabusService;
    @Inject protected MailNotificationFactory mailFactory;
    @Inject protected MailMessenger messenger;

    public BaseProjectAction() {
        super();
        injector.injectMembers(this);
    }
    protected User findFacilitator(String userName) {
        return userService.findUser(userName);
    }

    protected boolean validateProject(Project project) {
        if (project==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.project"))));
            return false;
        }

        if (!project.validate()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("message.project.requiredFields"))));
            return false;
        }

        if (project.getStartDate() == null || project.getFinishDate() == null) {
            addActionError(getText("error.project.missing.date.info"));
            return false;
        }
        if (project.getFinishDate().before(project.getStartDate())) {
            addActionError(getText("error.project.finishDate.earlierThanStart", Lists.newArrayList(dateFormat.format(project.getStartDate()), dateFormat.format(project.getFinishDate()))));
            return false;
        }

        /*
         * If the status is not null, check if the status is valid or not
         */
        if (project.getStatus()!=null) {
            if (!project.getStatus().validate() || !project.getStatus().isValidID()) {
                addActionError(getText("error.project.status.invalid", Lists.newArrayList(project.getStatus())));
                return false;
            }
        }

        return true;
    }


    protected void sendMailToFacilitator(Project project, User facilitator) throws AddressException, TemplateException, EmailException, IOException {
        mailFactory.setContextPath(ServletUtils.getBaseUrl(request));
        List<User> users = projectService.findProjectUsers(project,false);
        HtmlEmail email = mailFactory.buildNotificationForFacilitator(project, facilitator,users);
        messenger.send(new SendMailTask(email));
    }

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {}

    /**
     * subclasses should implement this method to put business logic
     *
     * @return
     */
    @Override
    protected String doExecuteImpl() {
        return null;
    }
}

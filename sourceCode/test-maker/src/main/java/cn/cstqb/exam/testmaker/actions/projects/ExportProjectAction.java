package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionImage;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.export.WrappedProject;
import cn.cstqb.exam.testmaker.json.serialization.JsonSerializer;
import cn.cstqb.exam.testmaker.services.IExportService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.util.HashUtil;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/8
 * Time: 22:26
 */
public class ExportProjectAction extends BaseProjectAction {
    @Inject
    private JsonSerializer serializer;
    @Inject private IQuestionService questionService;
    @Inject
    private IExportService exportService;
    private User sessionUser;
    private Project sessionProject;
    private String projectName;
    private Project project;
    private int count;
    private SimpleDateFormat sdf;
    public ExportProjectAction() {
        super();
        sdf = new SimpleDateFormat("yyyyMMdd");
        injector.injectMembers(this);
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        sessionUser = getLoggedInUser();
        sessionProject = (Project) session.get(Constants.ATTR_PROJECT);
        if (sessionUser==null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return;
        }

        if (sessionProject == null && Strings.isNullOrEmpty(projectName)) {
            addActionError(getText("error.project.export.missing"));
            return;
        }

        if (!Strings.isNullOrEmpty(projectName)) {
            project = projectService.find(projectName);
            if (project == null) {
                addActionError(getText("error.project.not.found", Lists.newArrayList(projectName)));
                return;
            }
        }

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        if (project == null) {
            project = projectService.find(sessionProject.getId());
        }
        logger.debug("ExportProjectAction.executeImpl: Exporting questions for project #0", project.getName());
        final List<String> excludedStatuses=configContext.getConfig().getStringList("application.export.excluded-statuses");
        logger.debug("ExportProjectAction.executeImpl: statuses excluded from export: #0", excludedStatuses );
        Collection<Question> questions = Collections2.filter(questionService.findAll(project), new Predicate<Question>() {
            @Override
            public boolean apply(@Nullable Question input) {
                if (input == null) {
                    return false;
                }
                String statusName=input.getStatus().getName();
                return !excludedStatuses.contains(statusName);
            }
        });
//        for (Question question : questions) {
//            for(QuestionImage questionImage : question.getImages()) {
//                questionImage.setPath("");
//            }
//
//        }
        this.count=questions!=null?questions.size():0;
        exportService.export(project);
        project.setExportedBy(sessionUser);
        project.setExportedOn(new Date());
        projectService.saveOrUpdate(project);
        return null;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCount() {
        return count;
    }
}

package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.question.BaseQuestionAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionRole;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;

import javax.mail.internet.AddressException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/16
 * Time: 22:50
 */
public class SaveProjectQuestion extends BaseQuestionAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("SaveProjectQuestion.executeImpl: Saving question..." );
        /*
         * If the incoming question does not have project attribute, then it is a new entity,
         * attach current session project to it
         */
        Project project = (Project) session.get(Constants.ATTR_PROJECT);
        if (project == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return Constants.RESULT_USER_NOT_AUTHENTICATED;
        }

        if (question.getProject() == null) {
            logger.debug("SaveProjectQuestion.executeImpl: Saving new question. Attaching it to project #0", project.getName() );
            question.setProject(project);
            question.setStatus(statusService.findStartStatus());
        }

        if (question.getId() == null) {
            questionService.saveOrUpdate(question);
            sendMailToChangedUsers(true);
        } else {
            sendMailToChangedUsers(false);
            questionService.saveOrUpdate(question);
        }
        return null;
    }

    private void sendMailToChangedUsers(boolean newQuestion) throws AddressException, TemplateException, EmailException, IOException {
        if (newQuestion) {
            sendMailToAllRoles(question);
        } else {
            Question persisted = questionService.findQuestion(question.getId());

            if (!question.getAuthor().getId().equals(persisted.getAuthor().getId())) {
                sendMailToQuestionUser(question, QuestionRole.Author);
            }
            if (!question.getQualityAdmin().getId().equals(persisted.getQualityAdmin().getId())) {
                sendMailToQuestionUser(question, QuestionRole.QA);
            }

            if ((!question.getReviewers().isEmpty())) {
                if (persisted.getReviewers().isEmpty()) {
                    sendMailToQuestionUser(question, QuestionRole.Reviewer);
                    return;
                }
                User current = Lists.newArrayList(question.getReviewers()).get(0);
                User original = Lists.newArrayList(persisted.getReviewers()).get(0);
                if (!current.getId().equals(original.getId())) {
                    sendMailToQuestionUser(question, QuestionRole.Reviewer);
                }
            }
        }
    }
}

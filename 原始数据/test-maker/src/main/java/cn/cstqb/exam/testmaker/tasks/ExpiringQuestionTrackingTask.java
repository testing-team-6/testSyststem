package cn.cstqb.exam.testmaker.tasks;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/3
 * Time: 17:43
 */
public class ExpiringQuestionTrackingTask extends AbstractProjectTrackTask {
    private int threshold;
    private Project project;
    private String contextPath;

    public ExpiringQuestionTrackingTask(Project project,int threshold) {
        this.threshold = threshold;
        this.project = project;
    }

    public ExpiringQuestionTrackingTask(Project project) {
        this.project = project;
    }

    public ExpiringQuestionTrackingTask(int threshold) {
        super();
        this.threshold = threshold;
    }

    public ExpiringQuestionTrackingTask() {
        super();
    }

    @Override
    void executeImpl() {
        if (threshold <1) {
            threshold = configContext.getConfig().getInt("monitoring.expiring.warning-threshold");
        }
        if (project != null) {
            logger.debug("ExpiringQuestionTrackingTask.executeImpl: checking provided project: {}", project.getName());
            checkProject(project);
        } else {
            logger.debug("ExpiringQuestionTrackingTask.executeImpl: checking all projects..." );
            List<Project> projects = projectService.findProjects(true);
            for (final Project project : projects) {
                checkProject(project);
            }
        }
    }

    private void checkProject(Project prj) {
        mailFactory.setContextPath(contextPath);
        List<User> users = prj.getUsers();
        for (final User user : users) {
            List<Question> questions = reportingService.getExpiringQuestions(prj, user.getUsername(), threshold);
            List<Question> expiredQuestions = reportingService.getExpiredQuestions(prj, user.getUsername());
            logger.info("ProjectStatusMonitoringJob.execute: {} expiring questions for user {} in project {}", questions != null ? questions.size() : 0, user.getUsername(), prj.getName());

            if (questions != null && !questions.isEmpty()) {
                HtmlEmail email;
                try {
                    email = mailFactory.newExpiringQuestionReminder(prj, user, questions, false);
                } catch (AddressException | EmailException | IOException | TemplateException e) {
                    e.printStackTrace();
                    logger.error("ProjectStatusMonitoringJob.execute: Failed to build html mail: {}", e);
                    continue;
                }
                if (email != null) {
                    messenger.send(new SendMailTask(email));
                }

            }

            if (expiredQuestions != null && !expiredQuestions.isEmpty()) {
                HtmlEmail email;
                try {
                    email = mailFactory.newExpiringQuestionReminder(prj, user, questions, true);
                } catch (AddressException | EmailException | IOException | TemplateException e) {
                    e.printStackTrace();
                    logger.error("ProjectStatusMonitoringJob.execute: Failed to build html mail: {}", e);
                    continue;
                }
                if (email != null) {
                    messenger.send(new SendMailTask(email));
                }
            }
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}

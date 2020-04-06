package cn.cstqb.exam.testmaker.scheduling.jobs;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.tasks.ExpiringQuestionTrackingTask;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/1
 * Time: 21:59
 */
public class ProjectStatusMonitoringJob extends AbstractJob {

    public ProjectStatusMonitoringJob() {
        super();
        injector.injectMembers(this);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ExpiringQuestionTrackingTask task = new ExpiringQuestionTrackingTask();
        if (context!=null) {
            JobDataMap params = context.getMergedJobDataMap();
            task.setContextPath(params.getString("baseURL"));
        }
        task.run();
    }
}

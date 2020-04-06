package cn.cstqb.exam.testmaker.tasks;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.inject.Inject;
import org.apache.commons.mail.HtmlEmail;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ExpiringQuestionTrackingTaskTest {
    private ExpiringQuestionTrackingTask task;
    @Inject
    MailNotificationFactory factory;
    @Inject
    IReportingService reportingService;
    @Inject
    IProjectService projectService;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Project project=projectService.findFirst();
        task = new ExpiringQuestionTrackingTask(project,7);

        task.run();

        Thread.sleep(5000);
    }
}

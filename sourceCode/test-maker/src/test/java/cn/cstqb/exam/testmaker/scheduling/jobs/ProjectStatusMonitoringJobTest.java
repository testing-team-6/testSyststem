package cn.cstqb.exam.testmaker.scheduling.jobs;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.scheduling.QuartzTriggerBuilder;
import com.google.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.quartz.*;

import java.util.Date;

import static org.junit.Assert.*;
import static org.quartz.TriggerBuilder.newTrigger;

public class ProjectStatusMonitoringJobTest {
    private ProjectStatusMonitoringJob job;
    @Inject
    private Scheduler scheduler;
    @Inject private ApplicationConfigContext configContext;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
        job = new ProjectStatusMonitoringJob();
    }

    @After
    public void tearDown() throws Exception {
        scheduler.shutdown();
    }

    @Test
    public void testExecute() throws Exception {
        Date runTime = DateBuilder.evenMinuteDateAfterNow();
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        JobDetail sendReminderJob = JobBuilder.newJob(ProjectStatusMonitoringJob.class)
                .withIdentity("Email-Reminder", "Routine")
                .usingJobData("baseURL", configContext.getConfigValue("application.base-url"))
                .build();
        scheduler.scheduleJob(sendReminderJob, trigger);

        scheduler.start();
//        job.execute(null);

        Thread.sleep(3 * 60L * 1000L);
    }
}

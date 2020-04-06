package cn.cstqb.exam.testmaker.listeners;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.scheduling.QuartzTriggerBuilder;
import cn.cstqb.exam.testmaker.scheduling.jobs.ProjectStatusMonitoringJob;
import com.google.inject.Inject;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/5
 * Time: 16:54
 */
public class QuartzListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(QuartzListener.class);
    private static AppInjector injector = AppInjector.getInstance();
    @Inject private ApplicationConfigContext configContext;
    @Inject private Scheduler scheduler;

    public QuartzListener() {
        injector.getInjector().injectMembers(this);
    }

    /**
     * Receives notification that the web application initialization
     * process is starting.
     * <p/>
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initializing Quartz Scheduler...");

        try {
            setupJobs();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("QuartzListener: Starting Quartz Scheduler failed: {}",e );
        }
    }

    private void setupJobs() throws SchedulerException {
        Trigger trigger = QuartzTriggerBuilder.buildExpireCheckTrigger();
        logger.debug("QuartzListener.setupJobs: [{}]", trigger.getClass().getName() );
        logger.debug("QuartzListener.setupJobs: next trigger fire time: [{}]", trigger.getNextFireTime() );
        JobDetail sendReminderJob = JobBuilder.newJob(ProjectStatusMonitoringJob.class)
                .withIdentity("Email-Reminder", "Routine")
                .usingJobData("baseURL",configContext.getConfigValue("application.base-url"))
                .build();
        scheduler.scheduleJob(sendReminderJob, trigger);
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p/>
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Shutting down Quartz Scheduler" );
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("QuartzListener.contextDestroyed: Shutting down schedule failed: {}",e );
        }
    }
}

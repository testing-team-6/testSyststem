package cn.cstqb.exam.testmaker.tasks;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/3
 * Time: 16:29
 */
public abstract class AbstractProjectTrackTask implements Runnable{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected static Injector injector = AppInjector.getInstance().getInjector();
    @Inject protected IReportingService reportingService;
    protected ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();


    @Inject protected IProjectService projectService;
    @Inject protected MailNotificationFactory mailFactory;
    @Inject protected MailMessenger messenger;

    protected AbstractProjectTrackTask() {
        injector.injectMembers(this);
    }

    abstract void executeImpl();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        executeImpl();
    }
}

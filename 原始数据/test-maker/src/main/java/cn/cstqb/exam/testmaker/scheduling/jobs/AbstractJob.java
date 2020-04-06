package cn.cstqb.exam.testmaker.scheduling.jobs;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.inject.Injector;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/1
 * Time: 21:28
 */
public abstract class AbstractJob implements Job {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject IReportingService reportingService;
    protected static Injector injector = AppInjector.getInstance().getInjector();
    protected ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();
    protected int warningThreshold;
    public AbstractJob() {
        injector.injectMembers(this);
        this.warningThreshold = configContext.getConfig().getInt("monitoring.expiring.warning-threshold");
    }
}

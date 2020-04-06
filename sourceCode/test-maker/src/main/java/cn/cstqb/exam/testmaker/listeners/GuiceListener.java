package cn.cstqb.exam.testmaker.listeners;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/10
 * Time: 16:19
 */
public class GuiceListener extends GuiceServletContextListener {
    private AppInjector appInjector;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        appInjector = AppInjector.getInstance();
        logger.info("GuiceListener.contextInitialized: Injector initialized: {}", appInjector.getClass().getName() );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
        logger.info("Stopping Guiced JPA Persistence Service....");
        getInjector().getInstance(PersistService.class).stop();
    }

    /**
     * Override this method to create (or otherwise obtain a reference to) your
     * injector.
     */
    @Override
    protected Injector getInjector() {

        return appInjector.getInjector();
    }


}

package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.Release;
import cn.cstqb.exam.testmaker.actions.UserValidator;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.json.serialization.JsonSerializer;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:03
 */
public class SharedModule extends AbstractModule {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(ApplicationConfigContext.class).in(Singleton.class);
        bind(JsonSerializer.class).in(Singleton.class);
        bind(Release.class).asEagerSingleton();

        bind(HtmlEmail.class).toProvider(HtmlEmailProvider.class);
        bind(MailMessenger.class).in(Singleton.class);
        bind(MailNotificationFactory.class).in(Singleton.class);
    }

    @Provides
    Scheduler provideQuartzScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("SharedModule.provideQuartzScheduler: Failed to get scheduler {}", e );
        }
        return  scheduler;
    }
}

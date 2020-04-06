package cn.cstqb.exam.testmaker.scheduling;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/5
 * Time: 17:20
 */
public class QuartzTriggerBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTriggerBuilder.class);
    private static ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();

    public static Trigger buildCronTrigger(String cronExpr, String triggerName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cronExpr));
        logger.debug("QuartzScheduleBuilder.buildExpireCheckTrigger: Building cron trigger for: \"{}\"", cronExpr );
        return newTrigger().withIdentity(triggerName, "Cron").withSchedule(cronSchedule(cronExpr))
                .build();
    }

    public static Trigger buildExpireCheckTrigger() {
        String expression = configContext.getConfig().getString("scheduling.expiring-check");
        logger.debug("QuartzScheduleBuilder.buildExpireCheckTrigger: Cron schedule from CONF: {}", expression );
        return buildCronTrigger(expression,"expire-check");
    }
}

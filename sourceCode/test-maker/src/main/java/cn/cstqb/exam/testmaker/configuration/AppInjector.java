package cn.cstqb.exam.testmaker.configuration;

import cn.cstqb.exam.testmaker.guice.ApplicationModule;
import cn.cstqb.exam.testmaker.guice.DaoModule;
import cn.cstqb.exam.testmaker.guice.JpaModule;
import cn.cstqb.exam.testmaker.guice.SharedModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:34
 */
public class AppInjector {
    private static Logger logger = LoggerFactory.getLogger(AppInjector.class);
    private Injector injector;
    private Injector parent;
    private UUID uuid;
    private static AppInjector instance;


    private AppInjector() {
        uuid = UUID.randomUUID();
        parent = Guice.createInjector(new SharedModule());
        init();
    }

    private void init() {
        List<Module> modules = Arrays.asList(new Module[]{
                new ApplicationModule()
        });
        this.injector = parent.createChildInjector(modules);
    }

    /**
     *
     * @return The singleton instance of AppInjector
     */
    public static AppInjector getInstance() {
        if (instance != null) {
            logger.debug("Getting existing {} instance. UUID=[{}]...", AppInjector.class.getName(), instance.getUuid());
        }else {
            instance = new AppInjector();
            if (logger.isDebugEnabled()) {
                logger.debug("Created new {} instance. UUID=[{}]...", AppInjector.class.getName(), instance.getUuid());
            }
        }
        return instance;
    }

    public Injector getInjector() {
        return injector;
    }

    public UUID getUuid() {
        return uuid;
    }
}

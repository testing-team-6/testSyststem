package cn.cstqb.exam.testmaker.junit.rules;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.guice.DaoModule;
import cn.cstqb.exam.testmaker.guice.JpaModule;
import cn.cstqb.exam.testmaker.guice.SharedModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:12
 */
public abstract class AbstractJpaRule extends ExternalResource {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Injector injector;
    protected AbstractJpaRule() {
        injector = AppInjector.getInstance().getInjector();
        Objects.requireNonNull(injector, "The App injector cannot be null;");
    }

    public Injector getInjector() {
        return injector;
    }

    /**
     * Override to set up your specific external resource.
     *
     * @throws Throwable if setup fails (which will disable {@code after}
     */
    @Override
    protected void before() throws Throwable {
        injector.getInstance(PersistService.class).start();
        _before();
    }

    protected abstract void _before() throws Throwable;

    /**
     * Override to tear down your specific external resource.
     */
    @Override
    protected void after() {
        _after();
        injector.getInstance(PersistService.class).stop();
    }

    protected abstract void _after();


}

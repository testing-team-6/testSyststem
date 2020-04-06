package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/24
 * Time: 22:10
 */
public class JpaModule extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        JpaPersistModule persistModule = new JpaPersistModule("test-maker");
        Properties properties= ApplicationConfigContext.getInstance().getJPAProperties();
        install(persistModule.properties(properties));
    }
}

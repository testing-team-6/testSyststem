/**
 *
 */
package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author gaojianm
 *
 */
public class ApplicationModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
        install(new JpaModule());
        install(new AppServletModule());
        install(new DaoModule());
        install(new ServiceModule());
        install(new WorkerModule());
	}

}

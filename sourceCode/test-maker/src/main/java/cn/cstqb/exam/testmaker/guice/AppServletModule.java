package cn.cstqb.exam.testmaker.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/20
 * Time: 19:39
 */
public class AppServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();
        filter("/*").through(PersistFilter.class);
    }
}

package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class JpaModuleTest {

    private Injector injector;

    @BeforeClass
    public static void startUp() {
        System.setProperty("jpa.hibernate.show_sql", "false");
    }

    @Before
    public void setUp() {
        injector = Guice.createInjector(new JpaModule());
        injector.getInstance(PersistService.class).start();
    }

    @After
    public void shutdown(){
        injector.getInstance(PersistService.class).stop();
    }

    @Test
    public void test(){
        boolean showSQL = ApplicationConfigContext.getInstance().getConfig().getBoolean("jpa.hibernate.show_sql");
        assertFalse(showSQL);
    }
}
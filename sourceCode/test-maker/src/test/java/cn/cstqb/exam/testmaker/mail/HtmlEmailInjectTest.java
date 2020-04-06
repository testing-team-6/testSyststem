package cn.cstqb.exam.testmaker.mail;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import com.google.inject.Inject;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/2
 * Time: 0:35
 */
public class HtmlEmailInjectTest {
    private static AppInjector injector = AppInjector.getInstance();
    @Inject private HtmlEmail email;

    @Before
    public void setUp() throws Exception {
        injector.getInjector().injectMembers(this);

    }

    @Test
    public void testName() throws Exception {
        assertNotNull(email);

    }
}

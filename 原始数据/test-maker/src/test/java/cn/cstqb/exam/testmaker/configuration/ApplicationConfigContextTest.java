package cn.cstqb.exam.testmaker.configuration;

import com.google.common.base.Strings;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

public class ApplicationConfigContextTest {
    private ApplicationConfigContext context;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jpa.hibernate.show_sql", "false");

        context=ApplicationConfigContext.getInstance();
    }

    @Test
    public void testGetConfig() throws Exception {
        System.out.println(context.getConfig().getConfig("jdbc").root().render());
    }

    @Test
    public void testGetJPAProperties() throws Exception {
        Properties properties=context.getJPAProperties();
        assertTrue(!properties.isEmpty());
        properties.list(System.out);
    }
    @Test
    public void testDynamicProperty() throws Exception {
        boolean showSQL = context.getConfig().getBoolean("jpa.hibernate.show_sql");
        assertFalse(showSQL);
    }

    @Test
    public void testGetQuestionDifficultyLevels(){
        Map<Integer, String> levels=context.getQuestionDifficultyLevels();
        assertTrue(!levels.isEmpty());
        System.out.println(levels);
    }

    @Test
    public void testGetUploadBaseDir() throws Exception {
        String baseDir = context.getUploadBaseDir();
        assertTrue(!Strings.isNullOrEmpty(baseDir));

    }
}
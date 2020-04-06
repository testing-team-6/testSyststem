package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExportProjectActionTest {
    private ExportProjectAction action;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        action = new ExportProjectAction();
        action.setProjectName("TEST-FIRST-PROJECT");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecute() throws Exception {
        String ret=action.execute();
        assertTrue(ret.equals("SUCCESS"));
    }
}

package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import javax.inject.Inject;


import static org.junit.Assert.*;

public class DeletePaperActionTest {
    private DeletePaperAction action;
    @Inject private IProjectService service;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();
    @Before
    public void setUp() throws Exception {
        action = new DeletePaperAction();

    }

    @Test
    public void validateInput() throws Exception {
        action.validateInput();
    }
    @Test
    public void testExecuteWithoutResult() throws Exception {
        assertNull(action.executeImpl());
    }
    @Test
    public void validateInputWithName() throws Exception {
        action.setProjectName("TEST-FIRST-PROJECT");
        action.validateInput();
    }

    @Test
    public void testExecuteWithResult() throws Exception {
        action = new DeletePaperAction();
        action.setProjectName("test0404");
        action.setId("12");
        assertNull(action.executeImpl());
    }

}
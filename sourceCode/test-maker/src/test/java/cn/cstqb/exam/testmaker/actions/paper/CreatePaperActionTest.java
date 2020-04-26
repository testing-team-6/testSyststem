package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;


import cn.cstqb.exam.testmaker.services.IQuestionService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CreatePaperActionTest  {

    private CreatePaperAction action;
    @Inject private IProjectService service;
    @Inject private IQuestionService questionService;
//    @Inject private Paper paper;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();
    @Before
    public void setUp() throws Exception {
        action = new CreatePaperAction();

    }
    @Test
    public void validateInput() throws Exception {
        action.validateInput();
    }

    @Test
    public void QuestionServiceTest() throws Exception {
        action.setQuestionService(questionService);
        assertEquals(questionService,action.getQuestionService());
    }
    @Test
    public void validateInputWithName() throws Exception {
        action.setProjectName("TEST-FIRST-PROJECT");
        action.validateInput();
    }
//    @Test
//    public void nullIdsTest() throws Exception{
//        action = new CreatePaperAction();
//        action.validateInput();
//        Paper paper = new Paper();
//        paper.setName("dfdfsdf");
//        action.setIds("-1");
//        action.setPaper(paper);
//        assertNull(action.executeImpl());
//
//    }
    @Test
    public void rightIdsTest() throws Exception{
        action = new CreatePaperAction();
        action.setProjectName("test0404");
        String strings = "4";
        action.setIds(strings);
        Paper paper = new Paper();
        paper.setName("abc");
        action.setPaper(paper);
        assertEquals(strings,action.getIds());
        assertNull(action.executeImpl());
    }

}

package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import com.opensymphony.xwork2.ActionProxy;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.SessionMap;

import org.junit.After;
import org.junit.Before;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;
import org.junit.Test;

import java.io.File;
import java.util.*;

public class ExportPaperActionTest extends StrutsTestCase {
    private IPaperService paperService;
    private IQuestionChoiceService questionChoiceService;

    @Before
    public void setUp() throws Exception{
        super.setUp();

        Mockery context = new JUnit4Mockery();
        paperService = context.mock(IPaperService.class);
        final Question question3 = new Question();
        question3.setType(new QuestionType("选择题"));
        question3.setScore((short)5);
        question3.setStem("题干");
        question3.setScenario("情景");
        question3.setMultipleChoice(true);

        final Question question66 = new Question();
        question66.setType(new QuestionType("选择题"));
        question66.setScore((short)4);
        question66.setStem("题干");
        question66.setScenario("情景");
        question66.setMultipleChoice(false);

        final Question question88 = new Question();
        question88.setType(new QuestionType("选择题"));
        question88.setScore((short)3);
        question88.setStem("题干");
        question88.setScenario("情景");
        question88.setMultipleChoice(true);

        context.checking(new Expectations() {
            {
                oneOf(paperService).find(3);
                Paper paper3 = new Paper();
                paper3.setName("试卷3");
                Set<Question> questionList3 = new HashSet<>();
                questionList3.add(question3);
                paper3.setQuestions(questionList3);
                will(returnValue(paper3));

                oneOf(paperService).find(66);
                Paper paper66 = new Paper();
                paper66.setName("试卷66");
                Set<Question> questionList66 = new HashSet<>();
                questionList66.add(question66);
                paper66.setQuestions(questionList66);
                will(returnValue(paper66));

                oneOf(paperService).find(88);
                Paper paper88 = new Paper();
                paper88.setName("试卷88");
                Set<Question> questionList88 = new HashSet<>();
                questionList88.add(question88);
                paper88.setQuestions(questionList88);
                will(returnValue(paper88));
            }
        });

        questionChoiceService = context.mock(IQuestionChoiceService.class);
        context.checking(new Expectations() {
            {
                oneOf(questionChoiceService).findQuestionChoices(question3);
                List<QuestionChoice> questionChoices3 = new LinkedList<>();
                QuestionChoice choice3 = new QuestionChoice();
                choice3.setChoiceLabel('a');
                choice3.setContent("选项");
                questionChoices3.add(choice3);
                will(returnValue(questionChoices3));

                oneOf(questionChoiceService).findQuestionChoices(question66);
                List<QuestionChoice> questionChoices66 = new LinkedList<>();
                QuestionChoice choice66 = new QuestionChoice();
                choice66.setChoiceLabel('a');
                choice66.setContent("选项");
                questionChoices66.add(choice66);
                will(returnValue(questionChoices66));

                oneOf(questionChoiceService).findQuestionChoices(question88);
                will(returnValue(null));
            }
        });
    }

    @After
    public void tearDown(){

    }

    public void testExecuteImpl1() throws Exception {
        request.setParameter("paperId", "3");
        ActionProxy proxy = getActionProxy("/web/admin/export-paper.action");

        Map<String, Object> sessionAttributes = new SessionMap<>(request);
        sessionAttributes.put("USER", new User());
        proxy.getInvocation().getInvocationContext().setSession(sessionAttributes);
        proxy.getInvocation().getInvocationContext();

        ExportPaperAction exportPaperAction = (ExportPaperAction)proxy.getAction();
        exportPaperAction.setPaperService(paperService);
        exportPaperAction.setQuestionService(questionChoiceService);
        String result = proxy.execute();
        System.out.println(result);
        System.out.println(exportPaperAction.getPaperId());
    }

    public void testExecuteImpl2() throws Exception {
        request.setParameter("paperId", "66");
        ActionProxy proxy = getActionProxy("/web/admin/export-paper.action");

        Map<String, Object> sessionAttributes = new SessionMap<>(request);
        sessionAttributes.put("USER", new User());
        proxy.getInvocation().getInvocationContext().setSession(sessionAttributes);
        proxy.getInvocation().getInvocationContext();

        ExportPaperAction exportPaperAction = (ExportPaperAction)proxy.getAction();
        exportPaperAction.setPaperService(paperService);
        exportPaperAction.setQuestionService(questionChoiceService);
        String result = proxy.execute();
        System.out.println(result);
        System.out.println(exportPaperAction.getPaperId());
    }

    public void testExecuteImpl3() throws Exception {
        request.setParameter("paperId", "88");
        ActionProxy proxy = getActionProxy("/web/admin/export-paper.action");

        Map<String, Object> sessionAttributes = new SessionMap<>(request);
        sessionAttributes.put("USER", new User());
        proxy.getInvocation().getInvocationContext().setSession(sessionAttributes);
        proxy.getInvocation().getInvocationContext();

        ExportPaperAction exportPaperAction = (ExportPaperAction)proxy.getAction();
        exportPaperAction.setPaperService(paperService);
        exportPaperAction.setQuestionService(questionChoiceService);
        String result = proxy.execute();
        System.out.println(result);
        System.out.println(exportPaperAction.getPaperId());
    }

    @Test(expected = Exception.class)
    public void testExecuteImpl4() throws Exception {
        request.setParameter("paperId", "9");
        ActionProxy proxy = getActionProxy("/web/admin/export-paper.action");

        Map<String, Object> sessionAttributes = new SessionMap<>(request);
        sessionAttributes.put("USER", new User());

        ExportPaperAction exportPaperAction = (ExportPaperAction)proxy.getAction();
        proxy.getInvocation().getInvocationContext().setSession(sessionAttributes);
        String result = proxy.execute();
        System.out.println(result);
        System.out.println(exportPaperAction.getPaperId());
    }

    public void testValidateInput() throws Exception {
        request.setParameter("paperId", "0");
        ActionProxy proxy = getActionProxy("/web/admin/export-paper.action");

        Map<String, Object> sessionAttributes = new SessionMap<>(request);
        sessionAttributes.put("USER", new User());

        ExportPaperAction exportPaperAction = (ExportPaperAction)proxy.getAction();
        proxy.getInvocation().getInvocationContext().setSession(sessionAttributes);
        String result = proxy.execute();
        System.out.println(result);
        System.out.println(exportPaperAction.getPaperId());
    }
}

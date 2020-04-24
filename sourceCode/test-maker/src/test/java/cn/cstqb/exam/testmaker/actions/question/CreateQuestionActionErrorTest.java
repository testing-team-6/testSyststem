package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static cn.cstqb.exam.testmaker.configuration.Constants.RESULT_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateQuestionActionErrorTest {
    @Inject
    private CreateQuestionAction action;
    @Inject private IQuestionService questionService;
    @Inject private Question question;
    private Mockery context = new JUnit4Mockery();
    @Inject
    private CreateQuestionAction createQuestionAction = new CreateQuestionAction();
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    public CreateQuestionActionErrorTest(){
        rule.getInjector().injectMembers(this);
    }
    @Before
    public void SetUp(){
        question = new Question();
        question.setId(500);
        action = Mockito.spy(createQuestionAction);
        Mockito.doReturn("pass").when(action).getText("error.question.not.found", Lists.newArrayList(question.getId()));
        doNothing().doThrow(new RuntimeException()).when((ActionSupport)action).addActionError("test pass");
        questionService = context.mock(IQuestionService.class);
    }
    @Test
    public void testCreateError() throws Exception {
        action.setQuestion(question);
        context.checking(new Expectations(){
            {
                allowing(questionService).exists(question);
                will(returnValue(false));
            }
        });
        action.questionService = this.questionService;
        assertEquals(RESULT_NOT_FOUND, action.executeImpl());
    }
    @After
    public void tearDown() throws Exception {

    }
}

package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.dao.*;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

import java.text.SimpleDateFormat;

import static cn.cstqb.exam.testmaker.configuration.Constants.RESULT_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateQuestionActionErrorTest {
    @Inject
    private CreateQuestionAction action;
    @Inject private IQuestionService questionService;
    @Inject private Question question;
    private  HttpServletRequest Request;
    private Mockery context = new JUnit4Mockery();
    @Inject private ProjectDao projectDao;
    @Inject private UserDao userDao;
    @Inject private QuestionTypeDao questionTypeDao;
    @Inject private QuestionLanguageDao questionLanguageDao;
    @Inject private QuestionStatusDao statusDao;
    @Inject private KnowledgePointDao knowledgePointDao;
    @Inject private MailNotificationFactory factory;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        action = Mockito.spy(createQuestionAction);
        question.setId(70);
        Mockito.doReturn("pass").when(action).getText("error.question.not.found", Lists.newArrayList(question.getId()));
        doNothing().doThrow(new RuntimeException()).when((ActionSupport)action).addActionError("test pass");
        questionService = context.mock(IQuestionService.class);
        Request = context.mock(HttpServletRequest.class);
        factory.setContextPath("http://localhost:8081/tm");
        action.mailFactory =factory;
        context.checking(new Expectations() {
            {
                atLeast(1).of(Request).getServerPort();
                will(returnValue(8081));
            }
        });
        context.checking(new Expectations(){
            {
                atLeast(1).of(Request).getScheme();
                will(returnValue("http"));
            }
        });
        context.checking(new Expectations(){
            {
                atLeast(1).of(Request).getServerName();
                will(returnValue("localhost"));
            }
        });
        context.checking(new Expectations(){
            {
                atLeast(1).of(Request).getContextPath();
                will(returnValue("tm"));
            }
        });
        context.checking(new Expectations(){
            {
                atLeast(1).of(questionService).saveOrUpdate(question);
                will(returnValue(null));
            }
        });
        action.setServletRequest(Request);
        action.questionService = this.questionService;
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
    @Test
    public void testCreateError2() throws Exception {
        question.setId(-10);
        Project project = projectDao.first();
        KnowledgePoint knowledgePoint = knowledgePointDao.first();
        QuestionType type=questionTypeDao.first();
        QuestionLanguage language=questionLanguageDao.first();
        User author = userDao.first();
        User qa=userDao.last();
        question.setProject(project);
        question.setKnowledgePoint(knowledgePoint);
        question.setType(type);
        question.setLanguage(language);
        question.setAuthor(author);
        question.setAuthoringStartDate(dateFormat.parse("2020-04-03"));
        question.setAuthoringFinishDate(dateFormat.parse("2020-05-10"));
        question.setReviewingStartDate(dateFormat.parse("2020-04-04"));
        question.setReviewingFinishDate(dateFormat.parse("2020-05-11"));
        question.setScore(knowledgePoint.getScore());
        question.setStatus(statusDao.findStart());
        question.setQualityAdmin(qa);
        question.setReviewers(Sets.newHashSet(qa));
        action.setQuestion(question);
        context.checking(new Expectations(){
            {
                allowing(questionService).exists(question);
                will(returnValue(true));
            }
        });
        action.questionService = this.questionService;
        assertEquals(null, action.executeImpl());
    }
    @Test
    public void testCreateError3() throws Exception {
        question.setId(40);
        Project project = projectDao.first();
        KnowledgePoint knowledgePoint = knowledgePointDao.first();
        QuestionType type=questionTypeDao.first();
        QuestionLanguage language=questionLanguageDao.first();
        User author = userDao.first();
        User qa=userDao.last();
        question.setProject(project);
        question.setKnowledgePoint(knowledgePoint);
        question.setType(type);
        question.setLanguage(language);
        question.setAuthor(author);
        question.setAuthoringStartDate(dateFormat.parse("2020-04-03"));
        question.setAuthoringFinishDate(dateFormat.parse("2020-05-10"));
        question.setReviewingStartDate(dateFormat.parse("2020-04-04"));
        question.setReviewingFinishDate(dateFormat.parse("2020-05-11"));
        question.setScore(knowledgePoint.getScore());
        question.setStatus(statusDao.findStart());
        question.setQualityAdmin(qa);
        question.setReviewers(Sets.newHashSet(qa));
        action.setQuestion(question);
        context.checking(new Expectations(){
            {
                allowing(questionService).exists(question);
                will(returnValue(true));
            }
        });
        action.questionService = this.questionService;
        assertEquals(null, action.executeImpl());
    }
    @After
    public void tearDown() throws Exception {

    }
}

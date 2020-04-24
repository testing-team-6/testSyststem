package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.dao.*;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.services.IQuestionService;

import com.google.common.collect.Sets;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateQuestionActionTest {
    @Inject private IQuestionService service;
    private Mockery context = new JUnit4Mockery();
    @Inject private CreateQuestionAction action;
    @Inject private QuestionDao dao;
    @Inject private ProjectDao projectDao;
    @Inject private UserDao userDao;
    @Inject private QuestionTypeDao questionTypeDao;
    @Inject private QuestionLanguageDao questionLanguageDao;
    @Inject private QuestionStatusDao statusDao;
    @Inject private KnowledgePointDao knowledgePointDao;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private  HttpServletRequest Request;
    @Inject private MailNotificationFactory factory;
    @Inject private Question question;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    public CreateQuestionActionTest() {
    }


    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
        factory.setContextPath("http://localhost:8081/tm");
        Request = context.mock(HttpServletRequest.class);
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
        action.setServletRequest(Request);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {
        question = new Question();
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
        action.mailFactory = factory;
        action.executeImpl();
    }
}

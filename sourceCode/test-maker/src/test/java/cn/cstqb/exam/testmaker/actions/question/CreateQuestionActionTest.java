package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
public class CreateQuestionActionTest{
    @Inject private IQuestionService service;
    @Inject private CreateQuestionAction action;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();



    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        Question question = service.findQuestion(1);
        service.delete(question);
        action.setQuestion(question);
        action.execute();
    }
}
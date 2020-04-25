package cn.cstqb.exam.testmaker.actions.questionChoice;

import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class DeleteQuestionChoiceActionTest {
    @Inject private IQuestionChoiceService service;
    @Inject private DeleteQuestionChoiceAction action;
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
        QuestionChoice choice = service.findQuestionChoice(1);
        action.setChoice(choice);
        action.execute();
    }
}

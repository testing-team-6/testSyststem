package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class QuestionStatusServiceTest {
    private IQuestionStatusService service;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
    	service = rule.getInjector().getInstance(IQuestionStatusService.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindTransitions() throws Exception {
        QuestionStatus status = service.findStatus(2);
        Set<QuestionStatus> transitions=service.findTransitionalStates(status);

        for (QuestionStatus transition : transitions) {
            System.out.println(transition);
        }
    }

    @Test
    public void testSaveTransitions() throws Exception {
        int size=service.findAllTransitions().size();
        QuestionStatus status = service.findStatus(2);
        Set<QuestionStatus> transitions = new HashSet<>();
        transitions.add(service.findStatus(5));
        transitions.add(service.findStatus(6));
        transitions.add(service.findStatus(7));
        service.saveTransitionalStates(status, transitions);
    }

    @Test
    public void testDeleteTransitions() throws Exception {
        int size=service.findAllTransitions().size();
        QuestionStatus status = service.findStatus(6);
        Set<QuestionStatus> transitions=service.findTransitionalStates(status);
        service.deleteTransitionalStates(status, transitions);
        assertEquals(size -6, service.findAllTransitions().size());
    }

    @Test
    public void testDelete() throws Exception {
        QuestionStatus status = service.findStatus(11);
        service.delete(status);
    }
}

package cn.cstqb.exam.testmaker.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

public class QuestionChoiceServiceTest {
	private IQuestionChoiceService service;

	@ClassRule
	public static DefaultJpaRule rule = new DefaultJpaRule();
	
	@Before
	public void setUp() throws Exception {
		service = rule.getInjector().getInstance(IQuestionChoiceService.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testFindQuestion() throws Exception {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorrectAnswerMatchType() throws Exception {
		QuestionChoice questionChoice = service.findQuestionChoice(1);
		assertTrue(service.correctAnswerMatchType(questionChoice));
	}
	
	@Test
	public void testHaveSameLabel() throws Exception {
		QuestionChoice questionChoice = service.findQuestionChoice(1);
		assertTrue(service.haveSameLabel(questionChoice));
	}

}

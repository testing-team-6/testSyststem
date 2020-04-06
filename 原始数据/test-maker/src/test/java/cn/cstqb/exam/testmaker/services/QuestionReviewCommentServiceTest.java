package cn.cstqb.exam.testmaker.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

public class QuestionReviewCommentServiceTest {

	private IReviewCommentService service;

	@ClassRule
	public static DefaultJpaRule rule = new DefaultJpaRule();

	@Before
	public void setUp() throws Exception{
		service = rule.getInjector().getInstance(IReviewCommentService.class);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCreate() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDelete()  throws Exception{
		fail("Not yet implemented");
	}

	@Test
	public void testFindFinalReviewComments() {
		fail("Not yet implemented");
	}

}

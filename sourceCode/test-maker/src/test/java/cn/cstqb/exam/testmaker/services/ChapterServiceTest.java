package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

import org.hibernate.sql.Delete;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChapterServiceTest {
	private IChapterService service;

	@ClassRule
	public static DefaultJpaRule rule = new DefaultJpaRule();

	@Before
	public void setUp() throws Exception {
		service = rule.getInjector().getInstance(IChapterService.class);
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void CLR() throws Exception {
		// delete the test data in database
	}

	@Test
	public void testSave() throws Exception {
		Syllabus newSyllabus = new Syllabus(203, "chapterServiceTest",
				"toBeCreated");
		Chapter newChapter = new Chapter("newChapter", newSyllabus);
		service.saveOrUpdate(newChapter);
		assertTrue(newSyllabus.getId() > 0);
	}

	@Test
	public void testUpdate() throws Exception {
		Syllabus updateSyllabus = new Syllabus(204, "chapterServiceTest",
				"toBeUpdated");
		Chapter updateChapter = new Chapter("toBeUpdatedChapter",
				updateSyllabus);
		service.saveOrUpdate(updateChapter);
		updateChapter.setTitle("updated");
		service.saveOrUpdate(updateChapter);
		assertTrue(updateChapter.getTitle() == "updatedChapter");
	}

	@Test
	public void delete() throws Exception {
		Syllabus deleteSyllabus = new Syllabus(205, "chapterServiceTest",
				"toBeDeleted");
		Chapter deleteChapter = new Chapter("toBeDeletedChapter",
				deleteSyllabus);
		service.saveOrUpdate(deleteChapter);
		service.delete(deleteChapter);
	}
}
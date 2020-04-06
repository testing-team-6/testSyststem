package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.util.HashUtil;

import com.google.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class KnowledgePointServiceTest {
    @Inject
	private IKnowledgePointService service;
    @Inject
    private ISyllabusService syllabusService;

	@ClassRule
	public static DefaultJpaRule rule = new DefaultJpaRule();

	@Before
	public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
	}

	@Test
	public void testSave() throws Exception {
		Syllabus newSyllabus = new Syllabus("knowledgePointServiceTest",
				"toBeCreated");
		Chapter newChapter = new Chapter("toBeCreatedChapter", newSyllabus);
		KnowledgePoint newKnowledgePoint = new KnowledgePoint(
				"toBeCreatedNumber", "toBeCreatedTitle", "toBeCreatedKLevel",
                (short) 2, newChapter);
		service.saveOrUpdate(newKnowledgePoint);
		assertTrue(newKnowledgePoint.getId() > 0);
	}

	@Test
	public void testUpdate() throws Exception {
		Syllabus updateSyllabus = new Syllabus("knowledgePointServiceTest",
				"toBeUpdated");
		Chapter updateChapter = new Chapter("toBeUpdatedChapter",
				updateSyllabus);
		KnowledgePoint updateKnowledgePoint = new KnowledgePoint(
				"toBeUpdatedNumber", "toBeUpdatedTitle", "toBeUpdatedKLevel",
                (short) 2, updateChapter);
		service.saveOrUpdate(updateKnowledgePoint);
		updateKnowledgePoint.setNumber("updatedNumber");
		service.saveOrUpdate(updateKnowledgePoint);
		assertTrue(updateKnowledgePoint.getNumber() == "updatedNumber");
	}

	@Test
	public void delete() throws Exception {
		Syllabus deleteSyllabus = new Syllabus("knowledgePointServiceTest",
				"toBeDeleted");
		Chapter deleteChapter = new Chapter("toBeDeletedChapter",
				deleteSyllabus);
		KnowledgePoint deleteKnowledgePoint = new KnowledgePoint(
				"toBeDeletedNumber", "toBeDeletedTitle", "toBeDeletedKLevel",
                (short) 2, deleteChapter);
		service.saveOrUpdate(deleteKnowledgePoint);
		service.delete(deleteKnowledgePoint);
	}

    @Test
    public void testFindBySyllabus() throws Exception {
        Syllabus syllabus = syllabusService.findSyllabus(1);
        List<KnowledgePoint> points = service.findKnowledgePoints(syllabus);
        assertFalse(points.isEmpty());
        System.out.println(points.size());
    }

    @Test
    public void testFindBySyllabusPaginated() throws Exception {
        Syllabus syllabus = syllabusService.findSyllabus(1);
        int pageSize=50;
        int pageNumber = 1;
        List<KnowledgePoint> points = service.findKnowledgePoints(syllabus, pageSize, pageNumber);
        assertFalse(points.isEmpty());
        System.out.println(points.size());
        for (KnowledgePoint point : points) {
            System.out.println(point);
        }
    }
}

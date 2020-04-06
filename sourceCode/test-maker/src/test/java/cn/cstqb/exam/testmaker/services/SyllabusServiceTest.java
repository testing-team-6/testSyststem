package cn.cstqb.exam.testmaker.services;

import java.util.List;
import java.util.Map;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.guice.DaoModule;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SyllabusServiceTest {
	private ISyllabusService service;

	@ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

	@Before
	public void setUp() throws Exception {
		service = rule.getInjector().getInstance(ISyllabusService.class);
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
    public static void CLR() throws Exception{
    	//delete the test data in db
    }

    @Test
    public void testExistsEmpty() throws Exception {
        Syllabus syllabus = new Syllabus();
        boolean exists = service.exists(syllabus);
        assertFalse(exists);
    }

    @Test
    public void testExistsEmpty1() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setLevel("");
        boolean exists = service.exists(syllabus);
        assertFalse(exists);
    }
    @Test
    public void testExistsNull() throws Exception {
        boolean exists = service.exists(null);
        assertFalse(exists);
    }

    @Test
    public void testExistsNonExistent() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setLevel("a;sdfja;sdkfj;asd");
        boolean exists = service.exists(syllabus);
        assertFalse(exists);
    }

    @Test
    public void testExistsNonExistent1() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setId(2999);
        boolean exists = service.exists(syllabus);
        assertFalse(exists);
    }

    @Test
    public void testExistsId() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setId(260);
        boolean exists = service.exists(syllabus);
        assertTrue(exists);
    }

    @Test
    public void testExistsLevel() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setLevel("ISTQB-31 (1422883721847)");
        boolean exists = service.exists(syllabus);
        assertTrue(exists);
    }

    @Test
    public void testExists() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setId(1);
        syllabus.setLevel("FL");
        boolean exists = service.exists(syllabus);
        assertTrue(exists);
    }

    @Test
    public void testExistsAll() throws Exception {
        Syllabus syllabus = new Syllabus();
        syllabus.setLevel("FL-1");
        syllabus.setVersion("2011");
        boolean exists = service.exists(syllabus);
        assertTrue(exists);
    }

    @Test
	public void testCreate() throws Exception {
		Syllabus newSyllabus = new Syllabus("syllabusServiceTest", "toBeCreated");
		service.saveOrUpdate(newSyllabus);
		assertTrue(newSyllabus.getId() > 0);
	}

	@Test
	public void testUpdate() throws Exception {
		Syllabus updateSyllabus = new Syllabus("syllabusServiceTest", "toBeUpdated");
		service.saveOrUpdate(updateSyllabus);
		updateSyllabus.setVersion("Updated");
		service.saveOrUpdate(updateSyllabus);
		assertTrue(updateSyllabus.getVersion() == "Updated");
	}

	@Test
	public void testDelete() throws Exception {
		Syllabus deleteSyllabus = new Syllabus("syllabusServiceTest", "toBeDeleted");
		service.saveOrUpdate(deleteSyllabus);
		service.delete(deleteSyllabus);
	}

	@Test
	public void testFindKnowledgePoints() throws Exception {
		//Chapter chapter = null;
		//List<KnowledgePoint> knowledgePoints = service.findKnowledgePoint(chapter);
	}

	@Test
	public void testFindKnowledgePoints1() throws Exception {
		//Syllabus syllabus = null;
		//Map<Chapter, List<KnowledgePoint>> knowledgePointsMap = service.findKnowledgePoint(syllabus);
	}

}

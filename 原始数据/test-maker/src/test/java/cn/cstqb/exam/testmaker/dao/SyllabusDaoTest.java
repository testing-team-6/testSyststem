package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class SyllabusDaoTest {
    private SyllabusDao dao;

    @ClassRule
    public static DefaultJpaRule jpaRule=new DefaultJpaRule();

    @Before
    public void setUp() {
        dao = jpaRule.getInjector().getInstance(SyllabusDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 10; i++) {
            String name = String.format("TM-%d-%d", i, System.currentTimeMillis());
            Syllabus syllabus = new Syllabus(name, "2012");
            dao.create(syllabus);
            assertTrue(syllabus.getId() > 0);
        }
    }

    @Test
    public void testUpdate() {
        Syllabus syllabus = dao.findById(dao.getMinID());
        syllabus.setLevel("FL");
        dao.update(syllabus);
    }

    @Test
    public void testGetAll(){
        List<Syllabus> syllabuses=dao.findAll();
        assertNotNull(syllabuses);
        System.out.println(syllabuses);
    }

    @Test
    public void testFindActive() {
        List<Syllabus> syllabuses = dao.findSyllabuses(false);
        assertNotNull(!syllabuses.isEmpty());
        for (Syllabus s : syllabuses) {
            System.out.printf("Disabled syllabus: %s\n", s);
        }
    }

    @Test
    public void testGetById() {
        Syllabus syllabus = dao.findById(dao.getMinID());
        assertNotNull(syllabus);
        System.out.println(syllabus);
    }

    @Test
    public void testMinID(){
        Integer id=dao.getMinID();
        assertNotNull(id);
    }
    @Test
    public void testMaxID() {
        dao.create(new Syllabus("TA", "2015"));
        Integer id = dao.getMaxID();
        assertNotNull(id);
    }

    @Test
    public void testDelete() {
        Syllabus syllabus = dao.findById(8);
        dao.delete(syllabus);
    }

    @Test
    public void testFindSyllabusesUsedByProjects() throws Exception {
        Syllabus syllabus=dao.findById(1);
        List<Project> projects = dao.findSyllabuses(syllabus);
        assertTrue(projects!=null && !projects.isEmpty());
    }
}

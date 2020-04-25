package cn.cstqb.exam.testmaker.services.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IPaperService;
import com.google.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class PaperServiceImplTest {
    @Inject
    private IPaperService paperService;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFind1() {
        Paper paper = paperService.find(2);
        assertEquals(paper.getName(), "试卷2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFind2() {
        Paper paper = paperService.find(0);
        assertEquals(paper.getName(), "试卷2");
    }

    @Test
    public void testSize() {
        assertEquals(paperService.size(), 3);
    }

    @Test
    public void testExists1() {
        Paper paper = new Paper();
        paper.setId(null);
        assertFalse(paperService.exists(paper));
    }

    @Test
    public void testExists2() {
        Paper paper = new Paper();
        paper.setId(-1);
        assertFalse(paperService.exists(paper));
    }

    @Test
    public void testExists3() {
        Paper paper = new Paper();
        paper.setId(1);
        assertTrue(paperService.exists(paper));
    }

    @Test
    public void testSaveOrUpdate1() {
        paperService.saveOrUpdate(null);
    }

    // create
    @Test(expected = IllegalStateException.class)
    public void testSaveOrUpdate2() {
        Paper paper = new Paper();
        paper.setId(4);
        paperService.saveOrUpdate(paper);
    }

    // create
    @Test
    public void testSaveOrUpdate3() {
        Paper paper = new Paper();
        paper.setName("试卷4");
        Project project = new Project();
        project.setId(1);
        paper.setProject(project);
        paperService.saveOrUpdate(paper);
        assertTrue(paperService.exists(paper));
    }

    // update
    @Test(expected = IllegalStateException.class)
    public void testSaveOrUpdate4() {
        Paper paper = paperService.find(1);
        paper.setName(null);
        paperService.saveOrUpdate(paper);
    }

    // update
    @Test
    public void testSaveOrUpdate5() {
        Paper paper = paperService.find(1);
        paper.setName("试卷5");
        paperService.saveOrUpdate(paper);
        assertTrue(paperService.exists(paper));
    }

    @Test
    public void testSaveOrUpdate6() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(PaperServiceImpl.class).setLevel(Level.OFF);
        paperService.saveOrUpdate(null);
    }

    @Test
    public void testDelete1() {
        int originSize = paperService.size();
        paperService.delete(null);
        assertEquals(originSize, paperService.size());
    }

    @Test
    public void testDelete2() {
        Project project = new Project();
        project.setId(1);
        List<Paper> papers = paperService.findAll(project);
        Paper paper = papers.get(0);
        assertTrue(paperService.exists(paper));
        paperService.delete(paper);
        assertFalse(paperService.exists(paper));
    }

    @Test
    public void testFindAll1() {
        Project project = new Project();
        project.setId(3);
        List<Paper> papers = paperService.findAll(project);
        assertEquals(papers.size(), 3);
        assertTrue(papers.contains(paperService.find(1)));
        assertTrue(papers.contains(paperService.find(2)));
        assertTrue(papers.contains(paperService.find(3)));
        for (Paper paper : papers) {
            System.out.println(paper.getId() + ":" + paper.getName());
        }
    }

    @Test
    public void testFindAll2() {
        List<Paper> papers = paperService.findAll("test0404");
        assertEquals(papers.size(), 3);
        assertTrue(papers.contains(paperService.find(1)));
        assertTrue(papers.contains(paperService.find(2)));
        assertTrue(papers.contains(paperService.find(3)));
        for (Paper paper : papers) {
            System.out.println(paper.getId() + ":" + paper.getName());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAll3() {
        paperService.findAll("");
    }

    @Test
    public void testFindAll4() {
        Project project = new Project();
        project.setId(3);
        List<Paper> papers = paperService.findAll(project, 5, 1);
        assertEquals(papers.size(), 3);
        assertTrue(papers.contains(paperService.find(1)));
        assertTrue(papers.contains(paperService.find(2)));
        assertTrue(papers.contains(paperService.find(3)));
        for (Paper paper : papers) {
            System.out.println(paper.getId() + ":" + paper.getName());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAll5() {
        paperService.findAll(null, 5, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAll6() {
        Project project = new Project();
        project.setId(3);
        paperService.findAll(project, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAll7() {
        Project project = new Project();
        project.setId(3);
        paperService.findAll(project, 5, 0);
    }
}

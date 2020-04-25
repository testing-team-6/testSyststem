package cn.cstqb.exam.testmaker.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PaperTest {
    private Project project0 = new Project();
    private Project project1 = new Project();
    String name0 = "huanggang";
    String name1 = "53";
    private Paper paper0 = new Paper(project0, name0);
    Question question0 = new Question();
    Set<Question> questions = new HashSet<Question>();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetProject() {
        Paper paper = new Paper(project0, name0);
        assertEquals(paper.getProject(), project0);
        Paper paper1 = new Paper();
        assertEquals(paper.getQuestions(), null);
    }

    @Test
    public void testSetProject() {
        Paper paper = new Paper(project0, name0);
        paper.setProject(project1);
        assertEquals(paper.getProject(), project1);
    }

    @Test
    public void testGetName() {
        Paper paper = new Paper(project0, name0);
        assertEquals(paper.getName(), name0);
    }

    @Test
    public void testSetName() {
        Paper paper = new Paper(project0, name0);
        paper.setName(name1);
        assertEquals(paper.getName(), name1);
    }

    @Test
    public void testGetQuestions() {
        paper0.setQuestions(questions);
        assertEquals(paper0.getQuestions(), questions);
    }

    @Test
    public void testSetQuestions() {
        paper0.setQuestions(questions);
        assertEquals(paper0.getQuestions(), questions);
    }

    @Test
    public void testAddQuestion() {
        paper0.setQuestions(questions);
        paper0.addQuestion(question0);
    }

    @Test
    public void testDeleteQuestion() {
        paper0.setQuestions(questions);
        paper0.addQuestion(question0);
        paper0.deleteQuestion(question0);
    }

    @Test
    public void testToString() {
        paper0.toString();
    }

    @Test
    public void testHashCode() {
        paper0.hashCode();
    }

    @Test
    public void testEquals() {
        Project project0 = new Project();
        Project project1 = new Project();
        Paper paper0 = new Paper(project0, name0);
        Paper paper1 = new Paper(project0, name0);
        Paper paper2 = new Paper(project0, name1);
//        Paper paper3 = new Paper(project1, name0);
        Paper paper4 = new Paper(null, name0);
        assertTrue(paper0.equals(paper0));
        assertTrue(paper0.equals(paper1));
//        assertTrue(paper0.equals(paper3));
        assertFalse(paper0.equals(paper4));
        assertFalse(paper0.equals(null));
        assertFalse(paper0.equals(new Object()));
        assertFalse(paper0.equals(paper2));

        paper0.setId(1);
        paper1.setId(2);
        assertFalse(paper0.equals(paper1));

        paper1.setId(1);
        paper0.setUpdatedOn(new Date());
        assertFalse(paper0.equals(paper1));

        paper0.setCreatedOn(new Date());
        paper1.setUpdatedOn(paper0.getUpdatedOn());
        assertFalse(paper0.equals(paper1));
    }

    @Test
    public void testValidate() {
        Paper paper0 = new Paper(project0, name0);
        Paper paper1 = new Paper(null, name0);
        Paper paper2 = new Paper(project0, null);
        Paper paper3 = new Paper(null, null);
        assertTrue(paper0.validate());
        assertFalse(paper1.validate());
        assertFalse(paper2.validate());
        assertFalse(paper3.validate());
    }
}
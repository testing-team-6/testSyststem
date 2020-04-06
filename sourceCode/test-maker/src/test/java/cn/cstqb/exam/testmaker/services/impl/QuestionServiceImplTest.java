package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionServiceImplTest {
    @Inject private IQuestionService service;
    @Inject private IProjectService projectService;
    @Inject private IUserService userService;
    @Inject private IKnowledgePointService knowledgePointService;

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
    public void testSaveOrUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindByAuthor() throws Exception {
        Project project = projectService.findFirst();
        User author = userService.findUser(1);
        List<Question> questions = service.findByAuthor(project, author);
        assertTrue(questions!=null && !questions.isEmpty());
        System.out.printf("%d questions found for author\n", questions.size());
    }

    @Test
    public void testFindByReviewer() throws Exception {
    	Project project = projectService.findFirst();
    	User reviewer = userService.findUser(1);
    	List<Question> questions = service.findByReviewer(project, reviewer.getUsername());
    	assertTrue(questions!=null && !questions.isEmpty());
    	System.out.printf("%d questions found for reviewer\n", questions.size());
    }

    @Test
    public void testFindByQA() throws Exception {
    	Project project = projectService.findFirst();
    	User QA = userService.findUser(2);
    	List<Question> questions = service.findByQA(project, QA.getUsername());
    	assertTrue(questions!=null && !questions.isEmpty());
    	System.out.printf("%d questions found for QA\n", questions.size());
    }

    @Test
    public void testFindAll1() throws Exception {

    }

    @Test
    public void testFindAll2() throws Exception {

    }

    @Test
    public void testFindAll3() throws Exception {

    }

    @Test
    public void testFindLanguages() throws Exception {

    }

    @Test
    public void testFindTypes() throws Exception {

    }

    @Test
    public void testFindQuestion() throws Exception {

    }

    @Test
    public void testExists() throws Exception {

    }

    @Test
    public void testUpdateKnowledgePoint() throws Exception {
        Project project=projectService.find(1);
        List<Question> questions = service.findAll(project);

        for (Question question : questions) {
            KnowledgePoint kp=knowledgePointService.findKnowledgePoint(question.getId());
            question.setKnowledgePoint(kp);
            service.saveOrUpdate(question);
        }
    }
}

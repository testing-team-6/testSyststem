package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IReportingService;
import cn.cstqb.exam.testmaker.services.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ReportingServiceImplTest {
    @Inject
    private IReportingService reportingService;
    @Inject IProjectService projectService;
    @Inject IUserService userService;

    private Project project;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
        project = projectService.findFirst();
    }

    @Test
    public void testGetExpiringQuestions() throws Exception {
        Project project1 = projectService.find(2);
        List<Question> questions=reportingService.getExpiringQuestions(project1, 7);
        for (Question question : questions) {
            System.out.println(String.format("%s - %s - %s", question.getId(), question.getKnowledgePoint().getTitle(), question.getStatus().getName()));
        }
    }

    @Test
    public void testGetTasks() throws Exception {
        User user = userService.findUser(1);
        Set<QuestionTaskEntry> tasks=reportingService.getTasks(project, user.getUsername());
        assertTrue(!tasks.isEmpty());
        for (QuestionTaskEntry task : tasks) {
            System.out.println(task);
        }
    }

    @Test
    public void testGetTaskSummary() throws Exception {
        User user = userService.findUser(1);
        QuestionTaskEntry summary=reportingService.getTaskSummary(project, user.getUsername());
        assertTrue(summary.getTotal()>0);
        System.out.println(summary);
    }

    @Test
    public void testGetAuthorTask() throws Exception {
        User user = userService.findUser("user-001");
        QuestionTaskEntry count = reportingService.getAuthorTask(project, user.getUsername());
        assertNotNull(count);
        System.out.println(count);
    }

    @Test
    public void testGetReviewerTask() throws Exception {
        User user = userService.findUser("user-001");
        QuestionTaskEntry count = reportingService.getReviewerTask(project, user.getUsername());
        assertTrue(count.getTotal()>0);
        System.out.println(count);
    }

    @Test
    public void testGetQATask() throws Exception {
        User user = userService.findUser("user-001");
        QuestionTaskEntry count = reportingService.getQATask(project, user.getUsername());
        assertTrue(count.getTotal()>0);
        System.out.println(count);
    }
}

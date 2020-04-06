package cn.cstqb.exam.testmaker.mailing;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.inject.Inject;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MailNotificationFactoryTest {
    @Inject MailNotificationFactory factory;
    @Inject
    IReportingService reportingService;
    @Inject
    IProjectService projectService;
    @Inject IQuestionService questionService;
    @Inject
    IQuestionChoiceService choiceService;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
    }

    @Test
    public void testNewExpiringQuestionReminder() throws Exception {
        Project project=projectService.findFirst();
        User user = project.getFacilitator();
        List<Question> questions=reportingService.getExpiringQuestions(project, 7);
        factory.setContextPath("http://localhost:8081/tm");
        HtmlEmail mail=factory.newExpiringQuestionReminder(project, user, questions, false);
        assertTrue(!mail.getToAddresses().isEmpty());

    }
    @Test
    public void testSendTask() throws Exception {
        Project project=projectService.findFirst();
        User user = project.getFacilitator();
        List<Question> questions=reportingService.getExpiringQuestions(project, 7);
        factory.setContextPath("http://localhost:8081/tm");
        HtmlEmail mail=factory.newExpiringQuestionReminder(project, user, questions,false);
        assertTrue(!mail.getToAddresses().isEmpty());

        SendMailTask task = new SendMailTask(mail);
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
    }

    @Test
    public void testQuestionUserTemplate() throws Exception {
        Question question = questionService.findQuestion(12);
        List<QuestionChoice> choices = choiceService.findQuestionChoices(12);
        QuestionRole role=QuestionRole.Author;
        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("choices", choices);
        params.put("project", question.getProject());
        params.put("user", question.getAuthor());
        params.put("role", role);
        params.put("ctx", null);
        String content=factory.processTemplate("/mail-templates/question-user-notification-mail.html.ftl",params);

        PrintWriter pw = new PrintWriter(String.format("%s\\question-user-notification-mail.html",
                System.getProperty("user.home")),"utf-8");
        pw.println(content);
        pw.close();
    }
    @Test
    public void testQuestionUserMail() throws Exception {
        Question question = questionService.findQuestion(12);
        List<QuestionChoice> choices = choiceService.findQuestionChoices(12);
        QuestionRole role=QuestionRole.Author;
        HtmlEmail email=factory.buildQuestionNotification(question, choices, role);
        SendMailTask mailer = new SendMailTask(email);
        mailer.run();
    }

    @Test
    public void testFacilitatorMsg() throws Exception {
        Project project = projectService.find(1);

        List<User> users=projectService.findProjectUsers(project,false);
        HtmlEmail email = factory.buildNotificationForFacilitator(project, project.getFacilitator(), users);
        SendMailTask mailer = new SendMailTask(email);
        mailer.run();

    }
}

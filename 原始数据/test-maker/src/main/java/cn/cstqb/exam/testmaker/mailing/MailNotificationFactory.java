package cn.cstqb.exam.testmaker.mailing;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.guice.HtmlEmailProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/3
 * Time: 15:54
 */
public class MailNotificationFactory {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    HtmlEmailProvider provider;
    @Inject
    ApplicationConfigContext configContext;
    private Configuration configuration;
    private ResourceBundle resourceBundle;
    private String contextPath;
    public MailNotificationFactory() {
        configuration= new Configuration(Configuration.VERSION_2_3_22);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        configuration.setDefaultEncoding("utf-8");
        resourceBundle = PropertyResourceBundle.getBundle("applicationResources");
    }


    public HtmlEmail newExpiringQuestionReminder(Project project, User user, List<Question> questions, Boolean expired) throws AddressException, EmailException, IOException, TemplateException {
        HtmlEmail email=provider.get();
        email.setTo(Lists.newArrayList(InternetAddress.parse(user.getEmail())));


        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("questions", questions);
        params.put("project", project);
        params.put("count", questions.size());
        params.put("ctx", contextPath);

        email.setSubject(MessageFormat.format(resourceBundle.getString("email.subject.expiring-questions-reminder"), project.getName()));

        //cc to facilitator is true and the incoming user is NOT facilitator himself
        boolean ccToFacilitator=configContext.getConfig().getBoolean("mail.ccToFacilitator");
        if (ccToFacilitator && !user.equals(project.getFacilitator())) {
            email.setCc(Lists.newArrayList(InternetAddress.parse(project.getFacilitator().getEmail())));
        }
        if (expired) {
            email.setHtmlMsg(processTemplate("/mail-tempaltes/expired-questions-reminder.htmel.ftl", params));
        } else {
            email.setHtmlMsg(processTemplate("/mail-templates/expiring-questions-reminder.html.ftl", params));
        }

        return email;
    }

    /**
     * Build notification sent to facilitator when a new project is created.
     * @param project
     * @param users
     * @return
     * @throws IOException
     * @throws AddressException
     * @throws EmailException
     * @throws TemplateException
     */
    public HtmlEmail buildNotificationForFacilitator(Project project, User facilitator, List<User> users) throws IOException, AddressException, EmailException, TemplateException {
        HtmlEmail email=provider.get();
        email.setTo(Lists.newArrayList(InternetAddress.parse(facilitator.getEmail())));

        Map<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("facilitator", facilitator);
        params.put("users", users);
        params.put("ctx", contextPath);

        email.setSubject(MessageFormat.format(resourceBundle.getString("email.subject.new.project.facilitator"), project.getName()));
        email.setHtmlMsg(processTemplate("/mail-templates/new-facilitator-notification.html.ftl", params));
        return email;
    }

    public HtmlEmail buildQuestionNotification(Question question, List<QuestionChoice> choices, QuestionRole role) throws EmailException, TemplateException, IOException {
        User user = null;
        switch (role) {
            case Author:
                user = question.getAuthor();
                break;
            case Reviewer:
                Set<User> reviewers = question.getReviewers();
                if (!reviewers.isEmpty()) {
                    user = reviewers.iterator().next();
                }
                break;
            case QA:
                user = question.getQualityAdmin();
                break;
        }
        if (user == null) {
            logger.warn("MailNotificationFactory.buildQuestionNotification: User is null. Null will be returned." );
            return null;
        }
        Project project = question.getProject();
        HtmlEmail email=provider.get();

        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("choices", choices);
        params.put("project", project);
        params.put("user", user);
        params.put("role", role);
        params.put("ctx", contextPath);

        email.addTo(user.getEmail());
        email.setSubject(MessageFormat.format(resourceBundle.getString("email.subject.question.user.assigned"),
                project.getName(),
                question.getId(),
                role.name()
        ));
        String content = processTemplate("/mail-templates/question-user-notification-mail.html.ftl", params);
        File message = new File(String.format("%s\\notification.html",
                System.getProperty("user.home")));
        PrintWriter pw = new PrintWriter(message,"utf-8");
        pw.println(content);
        pw.close();
        email.attach(message);
        email.setHtmlMsg(content);
        return email;
    }

    public void processTemplate(String path, Map<String, Object> params, Writer writer) throws IOException, TemplateException {
        Template template = configuration.getTemplate(path);
        template.process(params, writer);
    }

    public String processTemplate(String path, Map<String, Object> params) throws IOException, TemplateException {
        StringWriter buffer = new StringWriter();
        processTemplate(path, params, buffer);
        return buffer.toString();
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}

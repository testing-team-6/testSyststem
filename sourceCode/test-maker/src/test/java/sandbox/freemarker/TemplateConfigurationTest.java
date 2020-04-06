package sandbox.freemarker;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.inject.Inject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/2
 * Time: 12:14
 */
public class TemplateConfigurationTest {
    private Configuration configuration;
    @Inject private IProjectService projectService;
    @Inject private IReportingService reportingService;

    public TemplateConfigurationTest() {
        configuration= new Configuration(Configuration.VERSION_2_3_22);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
    }

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);

    }

    @Test
    public void testLoadTemplate() throws Exception {
        Template template = configuration.getTemplate("/mail-templates/expiring-questions-reminder.html.ftl");
        assertNotNull(template);

    }

    @Test
    public void testBuildView() throws Exception {
        Project project = projectService.findFirst();
        User facilitator = project.getFacilitator();
        List<Question> questions = reportingService.getExpiringQuestions(project, facilitator.getUsername(), 7);
        Template template = configuration.getTemplate("/mail-templates/expiring-questions-reminder.html.ftl");

        Map<String, Object> params = new HashMap<>();
        params.put("user", facilitator);
        params.put("questions", questions);
        params.put("project", project);
        params.put("count", questions.size());
        params.put("ctx", "http://localhost:8081/tm");


        Path output = Paths.get(System.getProperty("user.home"), "expiring-questions-reminder.html");
        PrintWriter pw = new PrintWriter(output.toFile(), "utf-8");

        template.process(params, pw);
        pw.close();

        assertTrue(Files.exists(output));
    }
}

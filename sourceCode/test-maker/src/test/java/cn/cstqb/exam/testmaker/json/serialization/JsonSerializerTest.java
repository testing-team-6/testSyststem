package cn.cstqb.exam.testmaker.json.serialization;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.export.WrappedProject;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class JsonSerializerTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Injector appInjector= AppInjector.getInstance().getInjector();
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();
    @Inject
    private JsonSerializer serializer;
    @Inject
    private IProjectService projectService;
    @Inject
    IQuestionService questionService;
    @Inject
    IUserService userService;
    @Inject
    ApplicationConfigContext configContext;

    public JsonSerializerTest() {
        appInjector.injectMembers(this);
    }

    @Test
    public void testWrite() throws Exception {
        Project project = new Project();
        project.setName("TEST_PROJECT");
        project.setStartDate(new Date());
        project.setFinishDate(new Date());

        String result=serializer.write(project);
        assertTrue(!Strings.isNullOrEmpty(result));
        logger.info("JsonSerializerTest.testWrite: \n{}", result );
    }

    @Test
    public void testWrite1() throws Exception {
        File output = new File(String.format("%s\\tm\\projects.json", System.getProperty("user.home")));
        List<Project> value = projectService.findProjects(false);
//        Project project = projectService.findFirst();
        serializer.write(output,value);
        assertTrue(output.exists());
    }

    @Test
    public void testExportQuestions() throws Exception {
        Project project = projectService.find(1);
        String exportBaseDir = configContext.getExportDir(project);
        Files.createDirectories(Paths.get(exportBaseDir));
        File output = Paths.get(exportBaseDir, String.format("%s.json",project.getName())).toFile();
        List<Question> questions = questionService.findAll(project);
//        serializer.write(output, questions);

        WrappedProject wp = new WrappedProject(project);
        serializer.write(output, wp);
        assertTrue(output.exists());
    }

    @Test
    public void testExportUsers() throws Exception {
        File output = new File(String.format("%s\\tm\\users.json", System.getProperty("user.home")));
        List<User> users = userService.findActivatedUsers();
        serializer.write(output, users);
        assertTrue(output.exists());

    }
}

package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.export.WrappedProject;
import cn.cstqb.exam.testmaker.export.WrappedQuestion;
import cn.cstqb.exam.testmaker.json.serialization.JsonSerializer;
import cn.cstqb.exam.testmaker.services.IExportService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.util.HashUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <div>
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao <br>
 * Date: 2015/11/9 <br>
 * Time: 19:47 <br>
 * </div>
 */
public class ExportServiceImpl implements IExportService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private final ApplicationConfigContext configContext;
    @Inject
    private JsonSerializer serializer;
    @Inject
    private IQuestionService questionService;

    @Inject
    private IQuestionChoiceService choiceService;
    @Inject
    public ExportServiceImpl(ApplicationConfigContext configContext) {
        this.configContext = configContext;
    }

    @Override
    public int export(Project project) throws IOException, NoSuchAlgorithmException {
        Preconditions.checkNotNull(project);
        WrappedProject wrappedProject = new WrappedProject(project);
        final List<String> excludedStatuses=configContext.getConfig().getStringList("application.export.excluded-statuses");
        logger.debug("ExportServiceImpl.export: statuses excluded from export: #0", excludedStatuses );
        Collection<Question> questions = Collections2.filter(questionService.findAll(project), new Predicate<Question>() {
            @Override
            public boolean apply(@Nullable Question input) {
                if (input == null) {
                    return false;
                }
                String statusName = input.getStatus().getName();
                return !excludedStatuses.contains(statusName);
            }
        });
        for (Question question : questions) {
            question.setChoices(new LinkedHashSet<>(choiceService.findQuestionChoices(question)));
        }
        wrappedProject.setQuestions(questions);
        int count=questions.size();
        logger.info("ExportServiceImpl.export: [{}] questions to be exported from project [{}]", count, project.getName());
        Path output = getProjectExportFile(project);
        serializer.write(output, wrappedProject);
        HashUtil.createCheckSum(output);
        return count;
    }

    private Path getProjectExportFile(Project project) throws IOException {
        Path path= Paths.get(configContext.getRepositoryBaseDir(), project.getName(), configContext.getConfigValue("application.export.dir-name"));
        logger.debug("ExportServiceImpl.getProjectExportFile: [{}]", path );
        Files.createDirectories(path);
        Path output = Paths.get(path.toString(),  String.format("%s_%s.json",project.getName(), sdf.format(new Date())));
        logger.debug("ExportServiceImpl.getProjectExportFile: project export file: [{}]",output );
        return output;
    }
}

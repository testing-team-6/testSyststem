package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.QuestionReportingDao;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;
import cn.cstqb.exam.testmaker.services.IReportingService;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 21:14
 */
public class ReportingServiceImpl implements IReportingService {
    private static final Logger logger = LoggerFactory.getLogger(ReportingServiceImpl.class);
    @Inject private QuestionReportingDao qsd;

    @Override
    public QuestionTaskEntry getProjectSummary(Project sessionProject) {
        return qsd.getProjectSummary(sessionProject);
    }

    /**
     * Get all questions that are near to the due date
     *
     * @param project
     * @param username
     * @param threshold
     * @return
     */
    @Override
    public List<Question> getExpiringQuestions(Project project, String username, int threshold) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        checkArgument(threshold>0,"The threshold must be greater than zero: %d", threshold);
        return filterQuestions(qsd.getExpiringQuestions(project, username, threshold));
    }

    /**
     *
     * @param project
     * @param username
     * @return
     */
    @Override
    public List<Question> getExpiredQuestions(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        return filterQuestions(qsd.getExpiredQuestions(project, username));
    }

    /**
     * @param project
     * @param threshold
     * @return
     */
    @Override
    public List<Question> getExpiringQuestions(Project project, int threshold) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(threshold>0,"The threshold must be greater than zero: %d", threshold);
        return filterQuestions(qsd.getExpiringQuestions(project, threshold));
    }

    @Override
    public Set<QuestionTaskEntry> getTasks(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        logger.debug("StatisticsServiceImpl: Getting task info for {}", username);
        return qsd.getTasks(project, username);
    }

    @Override
    public QuestionTaskEntry getTaskSummary(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        logger.debug("StatisticsServiceImpl: Getting task info for {}", username);
        return qsd.getTaskSummary(project, username);
    }

    @Override
    public QuestionTaskEntry getAuthorTask(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        logger.debug("StatisticsServiceImpl: Getting task info for {}", username);
        return qsd.getAuthorTask(project, username);
    }

    @Override
    public QuestionTaskEntry getReviewerTask(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        logger.debug("StatisticsServiceImpl: Getting task info for {}", username);
        return qsd.getReviewerTask(project,username);
    }

    @Override
    public QuestionTaskEntry getQATask(Project project, String username) {
        checkArgument(project!=null && project.validate() && project.isValidID(), "Invalid project provided: %s", project);
        checkArgument(!Strings.isNullOrEmpty(username));
        logger.debug("StatisticsServiceImpl: Getting task info for {}", username);
        return qsd.getQATask(project, username);
    }


    private List<Question> filterQuestions(Collection<Question> questions) {
        Collection<Question> filtered= Collections2.filter(questions, new Predicate<Question>() {
            @Override
            public boolean apply(@Nullable Question input) {
                if (input != null) {
                    return !input.getStatus().isFinish();
                } else {
                    return false;
                }
            }
        });

        return new ArrayList<>(filtered);
    }

}

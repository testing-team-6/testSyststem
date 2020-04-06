package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 21:13
 */
public interface IReportingService {
    Set<QuestionTaskEntry> getTasks(Project project, String username);

    QuestionTaskEntry getTaskSummary(Project project, String username);
    QuestionTaskEntry getAuthorTask(Project project, String username);
    QuestionTaskEntry getReviewerTask(Project project, String username);
    QuestionTaskEntry getQATask(Project project, String username);

    /**
     * Get all questions that are near to the due date
     * @param project
     * @param username
     * @param threshold
     * @return
     */
    List<Question> getExpiringQuestions(Project project, String username, int threshold);

    List<Question> getExpiredQuestions(Project project, String username);
    /**
     * 
     * @param project
     * @param threshold
     * @return
     */
    List<Question> getExpiringQuestions(Project project, int threshold);

    QuestionTaskEntry getProjectSummary(Project sessionProject);
}

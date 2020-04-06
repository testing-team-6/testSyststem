package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 20:31
 */
public interface QuestionReportingDao extends QuestionDao {

    /**
     * Finds the question statistics for the project and user
     * @param project
     * @param username
     * @return
     */
    Set<QuestionTaskEntry> getTasks(Project project, String username);

    QuestionTaskEntry getTaskSummary(Project project, String username);
    QuestionTaskEntry getAuthorTask(Project project, String username);
    QuestionTaskEntry getReviewerTask(Project project, String username);
    QuestionTaskEntry getQATask(Project project, String username);

    /**
     * Get the questions which are within the warning threshold
     * @param project
     * @param username
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

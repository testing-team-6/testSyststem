package cn.cstqb.exam.testmaker.actions.reporting;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/31
 * Time: 13:02
 */
public class QuestionSummaryAction extends BaseReportingAction {

    private QuestionTaskEntry summary;

    private Set<QuestionTaskEntry> tasks;

    private List<Question> questions;



    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        String currentUser=getLoggedInUser().getUsername();
        logger.debug("QuestionSummaryAction.executeImpl: Getting task summary info for #0 in project #1",  currentUser, sessionProject.getName());
        summary=reportingService.getTaskSummary(sessionProject, currentUser);
        tasks = reportingService.getTasks(sessionProject, currentUser);
        questions = reportingService.getExpiringQuestions(sessionProject, currentUser, warningThreshold);

        return null;
    }

    public void setSummary(QuestionTaskEntry summary) {
        this.summary = summary;
    }

    public QuestionTaskEntry getSummary() {
        return summary;
    }

    public Set<QuestionTaskEntry> getTasks() {
        return tasks;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}

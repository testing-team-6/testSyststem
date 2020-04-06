package cn.cstqb.exam.testmaker.actions.reporting;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.tasks.ExpiringQuestionTrackingTask;
import cn.cstqb.exam.testmaker.util.ServletUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/3
 * Time: 17:46
 */
public class NotifyExpiringQuestionsAction extends BaseReportingAction {
    private ExpiringQuestionTrackingTask task;
    private int threshold;

    public NotifyExpiringQuestionsAction() {
        super();
        this.task = new ExpiringQuestionTrackingTask();
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (threshold<1) {
            threshold = configContext.getConfig().getInt("monitoring.expiring.warning-threshold");
        }
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("CheckExpiringQuestionsAction.executeImpl: {}", threshold );
        Project sessionProject = (Project) session.get(Constants.ATTR_PROJECT);
        task.setProject(sessionProject);
        task.setContextPath(ServletUtils.getBaseUrl(request));
        task.run();
        return null;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}

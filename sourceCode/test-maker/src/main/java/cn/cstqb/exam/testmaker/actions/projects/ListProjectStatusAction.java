package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.entities.ProjectStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/25
 * Time: 18:46
 */
public class ListProjectStatusAction extends BaseProjectAction {
    List<ProjectStatus> statuses;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        statuses = projectStatusService.findAll();
        logger.info("ListProjectStatusAction.executeImpl: Statuses found: #0", statuses.size()+"" );
        return null;
    }

    public List<ProjectStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ProjectStatus> statuses) {
        this.statuses = statuses;
    }
}

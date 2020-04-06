package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.entities.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/7
 * Time: 2:12
 */
public class ListProjectAction extends BaseProjectAction {
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
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

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        this.projects = projectService.findProjects(false);
        logger.debug("ListProjectAction.execute: projects loaded: #0", projects.size() );
        return null;
    }

    /**
     * action to list active projects only
     * @return
     */
    public String active() {
        this.projects = projectService.findProjects(true);
        logger.debug("ListProjectAction.execute: Active projects loaded: #0", projects.size() );
        return SUCCESS;
    }
}

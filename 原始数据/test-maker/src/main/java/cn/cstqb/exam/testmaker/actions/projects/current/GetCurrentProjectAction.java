package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.projects.BaseProjectAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/8
 * Time: 21:59
 *
 * Loads the project for the current logged in use
 */
public class GetCurrentProjectAction  extends BaseCurrentProjectAction {
    private Project project;

    @Override
    protected String executeImpl() throws Exception {
        this.project = projectService.find(sessionProject.getId());
        logger.debug("GetCurrentProjectAction.executeImpl: #0", project.toString());
        return null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

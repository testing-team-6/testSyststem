package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/31
 * Time: 8:46
 */
public class CreateProjectAction extends BaseProjectAction {

    private Project project;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (!validateProject(project)) return;
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        if (project.getStatus()==null) {//assign a default initial state when the project does not have status object.
            logger.trace("CreateProjectAction.executeImpl: The project does not have status associated. #0", project.toString() );
            project.setStatus(projectStatusService.findStartState());
        }
        logger.debug("CreateProjectAction.create: Project details from input method #0", project.toString());
        User facilitator = project.getFacilitator();
        project.addUser(facilitator);
        projectService.saveOrUpdate(project);
        logger.debug("CreateProjectAction.executeImpl: Sending email to facilitator #0", project.getFacilitator().getUsername() );
        sendMailToFacilitator(project, facilitator);
        return null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.inject.Inject;

import java.util.Date;

public class LoginAction extends BaseAction {
    @Inject
    private IProjectService projectService;
    @Inject
    private IUserService userService;
    private int projectId;

    public LoginAction() {
        injector.injectMembers(this);
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
        logger.debug("Finding project with id: #0", projectId);
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return Constants.RESULT_USER_NOT_AUTHENTICATED;
        }
        userService.updateLastLogin(loggedInUser.getUsername(), new Date());
        Project project = projectService.find(projectId);
        session.put(Constants.ATTR_PROJECT, project);
        session.put(Constants.ATTR_FACILITATOR, project.getFacilitator().getUsername().equalsIgnoreCase(loggedInUser.getUsername()));
        return null;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}

package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.services.IProjectService;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/29
 * Time: 21:20
 */
public class AuthenticateAction extends AbstractLoginAction {
    @Inject
    private IProjectService projectService;
    private List<Project> projects;


    public AuthenticateAction() {
        super();
        injector.injectMembers(this);
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        String result = super.executeImpl();
        if (result != null) {
            return Constants.RESULT_FORM_INVALID;
        }
        //remove inactive projects
        this.projects = projectService.findProjects(user, true);
        logger.debug("#0 projects found for user #1", projects != null ? projects.size() : 0, username);

        if (projects == null || projects.isEmpty()) {
            logger.info("No project found for this user. Authentication will be blocked");
            addActionError(getText("error.user.auth.noProject"));
            return Constants.RESULT_NOT_FOUND;
        }

        session.put(Constants.ATTR_USER, user);
        return null;
    }

    public List<Project> getProjects() {
        return projects;
    }
}

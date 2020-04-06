package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.projects.BaseProjectAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IQuestionService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/15
 * Time: 23:40
 */
public abstract class BaseCurrentProjectAction extends BaseProjectAction {

	@Inject
    protected IQuestionService questionService;
    protected Project sessionProject;
    protected User sessionUser;
    protected BaseCurrentProjectAction() {
        super();
        injector.injectMembers(this);
    }

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {
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
        sessionUser = getLoggedInUser();
        sessionProject = (Project) session.get(Constants.ATTR_PROJECT);
        if (sessionUser==null|| sessionProject == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return;
        }
        validateProject(sessionProject);
    }
}

package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.actions.projects.current.BaseCurrentProjectAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/15
 * Time: 0:26
 */
public class UpdateLastLoginAction extends BaseCurrentProjectAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return Constants.RESULT_USER_NOT_AUTHENTICATED;
        }
        userService.updateLastLogin(loggedInUser.getUsername(), new Date());
        return null;
    }
}

package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;

public class AdminLoginAction extends AbstractLoginAction {
    public AdminLoginAction() {
        super();
        adminLogin = true;
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
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            addActionError(getText("error.user.auth.usernameAndPasswordRequired"));
            return;
        }
        user = userService.findUser(username);
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        /*
         * Automatically creates system admin if no user in db
         */
        if (userService.count() == 0) {
            logger.warn("AdminLoginAction.executeImpl: No users exist in the system.");
            User builtInUser = configContext.getBuiltInUser();
            if (username.equals(builtInUser.getUsername())) {
                logger.debug("AdminLoginAction.executeImpl: No user in system yet. Creating system built-in user[{}]", username);
                user = userService.createBuiltInUser();
            }
            saveUser();
            return null;
        }

        if (user != null && !user.isAdmin()) {
            addActionError(getText("error.user.auth.admin.required"));
            return Constants.RESULT_USER_NOT_AUTHORIZED;
        }
        return super.executeImpl();
    }

    @Override
    protected void saveUser() {
        super.saveUser();
        session.put(Constants.ATTR_ADMIN_USER, user.getUsername());
    }

}

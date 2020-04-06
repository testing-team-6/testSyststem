package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import javax.security.auth.login.LoginException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/11
 * Time: 8:37
 */
public class ChangePasswordAction extends BaseCurrentProjectAction {
    private String oldPassword;
    private String newPassword;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        super.validateInput();
        if (Strings.isNullOrEmpty(oldPassword) || Strings.isNullOrEmpty(newPassword)) {
            addActionError(getText("error.user.password.empty"));
            return;
        }

        if (oldPassword.equals(newPassword)) {
            addActionError(getText("error.user.password.change.sameAsOld"));
            return;
        }

        if (newPassword.matches(".*?\\s.*?")) {
            addActionError(getText("error.user.password.containingSpace"));
            return;
        }

        int minLength = configContext.getConfig().getInt("application.security.password-min-length");
        if (newPassword.length() < minLength) {
            addActionError(getText("error.user.password.tooShort", Lists.newArrayList(minLength)));
            return;
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
        logger.debug("ChangePasswordAction.executeImpl: changing password for #0", sessionUser.getUsername() );
        try {
            userService.authenticate(sessionUser.getUsername(), oldPassword);
        } catch (LoginException e) {
            addActionError(getText("error.loginFailed"));
            addActionError(getText("error.user.password.change.oldPasswordInvalid"));
            return Constants.RESULT_USER_LOGIN_FAILED;
        }

        boolean result=userService.changePassword(sessionUser.getUsername(), oldPassword, newPassword);
        if (!result) {
            addActionError(getText("error.user.password.change.unknownError"));
            return ERROR;
        }
        logger.debug("ChangePasswordAction.executeImpl: password changed for #0", sessionUser.getUsername() );
        return null;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

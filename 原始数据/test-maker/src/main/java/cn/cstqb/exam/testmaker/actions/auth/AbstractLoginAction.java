package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.Map;

public abstract class AbstractLoginAction extends BaseAction {
    protected String username;
    protected String password;
    protected boolean adminLogin;
    protected transient User user;
    @Inject
    IUserService userService;

    public AbstractLoginAction() {
        super();
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
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            addActionError(getText("error.user.auth.usernameAndPasswordRequired"));
            return;
        }
        user = userService.findUser(username);
        if (user == null) {
            addActionError(getText("error.loginFailed"));
            return;
        }
        if (!user.isEnabled()) {
            addActionError(getText("error.user.locked"));
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
        logger.debug("Attempting to log in user: [#0]", username);

        try {
            userService.authenticate(username, password, adminLogin);
        } catch (LoginException e) {
            logger.error("Login failed: #0", e.getMessage());
            addActionError(getText("error.loginFailed"));
            return ERROR;
        }
        saveUser();
        return null;
    }


    protected void saveUser() {
        logger.debug("AbstractLoginAction.saveUser: Saving user info into session: #0", username);
        session.put(Constants.ATTR_USER, user);

        //TODO: how to put roles into session
    }

    public String logout() {
        if (logger.isInfoEnabled()) {
            logger.info("Logging out user {}", username);
        }
        session.clear();
        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

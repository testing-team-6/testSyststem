package cn.cstqb.exam.testmaker.actions;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/5/25
 * Time: 23:06
 */
public class UserValidator extends ActionSupport {
    private IUserService userService;

    @Inject
    public UserValidator(IUserService userService) {
        this.userService = userService;
    }

    public boolean validateUser(User user) {
        if (user==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.user"))));
            return false;
        }

        if (!user.validate()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.user"), getText("message.user.requiredFields"))));
            return false;
        }

        if(!validateUserName(user.getUsername())) return false;
        return validateEmail(user.getEmail());
    }

    public boolean validateUserName(String userName) {
        if(Strings.isNullOrEmpty(userName)) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.user.username"))));
            return false;
        }
        if (userName.length() < 2) {
            addActionError(getText("error.entity.field.text.tooShort", Lists.newArrayList(getText("label.entity.user.username"), 2)));
            return false;
        }
        if (!userName.matches("(?i)[a-z0-9\\._\\-@]+")) {
            addActionError(getText("error.user.name.invalid"));
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            addActionError(getText("error.user.email.empty"));
            return false;
        }
        if (email.length() < 3) {
            addActionError(getText("error.user.email.invalid", Lists.newArrayList("")));
            return false;
        }

        if (!email.matches(".+@.+")) {
            addActionError(getText("error.user.email.invalid", Lists.newArrayList("")));
            return false;
        }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            addActionError(getText("error.user.email.invalid", Lists.newArrayList(ex.getMessage())));
            return false;
        }
        return true;
    }

    /**
     * Simulates a struts2 action but returns error message instead.
     * @param userName
     * @return Error message
     */
    protected String checkCreateUser(String userName) {
        if (!validateUserName(userName)) {
            return Constants.RESULT_FORM_INVALID;
        }
        if (userService.isUserNameExists(userName)) {
            addActionError(getText("error.user.name.alreadyExists"));
            return Constants.RESULT_FORM_INVALID;
        }
        return SUCCESS;
    }

    /**
     *
     * @param userName
     * @return
     */
    public String validateExistingUser(String userName) {
        if (!validateUserName(userName)) {
            return Constants.RESULT_FORM_INVALID;
        }
        User user = userService.findUser(userName);
        if (user==null) {
            addActionError(getText("error.user.not.found"));
            return Constants.RESULT_NOT_FOUND;
        }else if (!user.isEnabled()) {
            addActionError(getText("error.user.locked"));
            return Constants.RESULT_USER_LOCKED;
        }

        return SUCCESS;
    }

    public boolean validateProjectUser(Project project, final String username) {
        if (!validateUserName(username)) return false;
        if (!userService.isUserNameExists(username)) {
            addActionError(getText("error.user.not.found", Lists.newArrayList(username)));
            return false;
        }
        return !Collections2.filter(project.getUsers(),new Predicate<User>() {
            @Override
            public boolean apply(User input) {
                return input.getUsername().equals(username);
            }
        }).isEmpty();
    }
}

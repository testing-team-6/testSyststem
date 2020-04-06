package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/10
 * Time: 23:37
 */
public class UpdateProfileAction extends BaseCurrentProjectAction {
    private String fullName;
    private String phone;
    private String email;
    private boolean isModified;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        //super.validateInput();
        sessionUser = getLoggedInUser();
        if (Strings.isNullOrEmpty(fullName)) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.user.fullName"))));
            return;
        }
        if (Strings.isNullOrEmpty(email)) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.user.email"))));
            return;
        }
        if (!email.equals(sessionUser.getEmail()) && userService.isEmailExists(email)) {
            addActionError(getText("error.user.email.alreadyUsed", Lists.newArrayList(email)));
        }

    }

    public String checkIsModified() throws Exception {
        validateInput();
        if (!email.equals(sessionUser.getEmail())
            || !fullName.equals(sessionUser.getFullName())
            ||!phone.equals(sessionUser.getPhone())) {
            isModified = true;
        }
        logger.warn("UpdateProfileAction.checkIsModified: Is user info modified? [#0]", isModified);
        return SUCCESS;
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("UpdateProfileAction.executeImpl: update profile for user： #0", sessionUser.getUsername() );
        checkIsModified();

        if (!fullName.equals(sessionUser.getFullName())) {
            sessionUser.setFullName(fullName);
        }
        if (!email.equals(sessionUser.getEmail())) {
            sessionUser.setEmail(email);
        }
        if (!phone.equals(sessionUser.getPhone())) {
            sessionUser.setPhone(phone);
        }
        if (!isModified) {
            return null;
        }
        User updatedUser=userService.update(sessionUser);
        session.put(Constants.ATTR_USER, updatedUser);
        logger.debug("UpdateProfileAction.executeImpl: User profile updated successfully." );
        return null;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isModified() {
        return isModified;
    }
}

package cn.cstqb.exam.testmaker.actions.user;

import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.util.HashUtil;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/24
 * Time: 23:06
 */
public class ResetPasswordAction extends BaseUserAction {
    private User user;
    private String tempPassword;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (user==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.user"))));
            return;
        }

        if (!user.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(user.getId())));
            return;
        }
        if (userService.findUser(user.getId()) == null) {
            addActionError(getText("error.user.not.found", Lists.newArrayList(user.getUsername())));
            return;
        }
        String username = user.getUsername();
        if (!userValidator.validateUserName(username)){
            return ;
        }
        if (!userService.isUserNameExists(username)) {
            logger.warn("ManageExistingUserAction.validateSingleUser: User name does not exist: {}", username );
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
        logger.debug("ResetPasswordAction.executeImpl: Resetting password for #0", user.getUsername() );
        User persisted = userService.findUser(user.getId());
        this.tempPassword= HashUtil.generateRandom();
        persisted.setPassword(tempPassword);
        userService.update(persisted);

        //TODO email should be sent to admin and user instead of sending the password back
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
}

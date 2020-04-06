package cn.cstqb.exam.testmaker.actions.user;

import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/22
 * Time: 19:03
 */
public class CreateUserAction extends BaseUserAction {
    private User user;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        logger.debug("CreateUserAction.validateInput: input user via JSON", Objects.toString(user) );
        if(!userValidator.validateUser(user)) return;

        if (userService.isUserNameExists(user.getUsername())) {
            addActionError(getText("error.entity.field.alreadyExists", Lists.newArrayList(getText("label.entity.user.username"), user.getUsername())));
            return;
        }

        if (userService.isEmailExists(user.getEmail())) {
            addActionError(getText("error.entity.field.alreadyExists", Lists.newArrayList(getText("label.entity.user.email"), user.getEmail())));
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
        logger.debug("CreateUserAction.executeImpl: Creating USER: [{}]",user );
        String encodedPassword = user.getPassword();
        String password = new String(BaseEncoding.base64().decode(encodedPassword), Charsets.UTF_8);
        logger.trace("CreateUserAction.executeImpl: decoded password: {}", password );
        user.setPassword(password);
        userService.createUser(user);
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

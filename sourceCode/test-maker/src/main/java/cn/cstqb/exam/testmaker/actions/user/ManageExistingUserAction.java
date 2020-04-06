package cn.cstqb.exam.testmaker.actions.user;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/22
 * Time: 23:08
 */
public class ManageExistingUserAction extends BaseUserAction {
    private List<User> users;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (users==null || users.isEmpty()) {
            addActionError(getText("error.status.notModified", Lists.newArrayList(getText("label.entity.user"))));
            return;
        }
        Iterator<User> iterator=users.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (!validateSingleUser(user)) {
                logger.warn("ManageExistingUserAction.validateInput: User is invalid. Removing from queue...", Objects.toString(user) );
                iterator.remove();
                count++;
            }
        }
        logger.info("ManageExistingUserAction.validateInput: Invalid users removed: "+ count );
    }

    private boolean validateSingleUser(User user) {
        if (user==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.user"))));
            return false;
        }

        if (!user.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(user.getId())));
            return false;
        }
        if (userService.findUser(user.getId()) == null) {
            addActionError(getText("error.user.not.found", Lists.newArrayList(user.getUsername())));
            return false;
        }
        String username = user.getUsername();
        if (!userValidator.validateUserName(username)){
            return false;
        }
        if (!userService.isUserNameExists(username)) {
            logger.warn("ManageExistingUserAction.validateSingleUser: User name does not exist: {}", username );
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     */
    public String toggleAdminState() {
        if (!isValidInput()) {
            logger.error("ManageExistingUserAction.deactivate: Error in provided user details: [{}]", users );
            return Constants.RESULT_FORM_INVALID;
        }
        try{
            for (User user : users) {
                User persisted = userService.findUser(user.getId());
                persisted.setAdmin(!persisted.isAdmin());
                userService.update(persisted);
            }
        }catch (Exception e){
            this.users.clear();
            addActionError(getText("error.exception", Lists.newArrayList(e.getMessage())));
            return ERROR;
        }

        this.users=null;
        return SUCCESS;
    }

    /**
     * Toggle use activation state
     * @return
     * @throws Exception
     */
    public String toggleState(){
        if (!isValidInput()) {
            logger.error("ManageExistingUserAction.deactivate: Error in provided user details: [{}]", users );
            return Constants.RESULT_FORM_INVALID;
        }
        try{
            for (User user : users) {
                User persisted = userService.findUser(user.getId());
                persisted.setEnabled(!persisted.isEnabled());
                userService.update(persisted);
            }
        }catch (Exception e){
            this.users.clear();
            addActionError(getText("error.exception", Lists.newArrayList(e.getMessage())));
            return ERROR;
        }

        this.users=null;
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
        return null;
    }

    private boolean isValidInput() {
        validateInput();
        return !hasActionErrors();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

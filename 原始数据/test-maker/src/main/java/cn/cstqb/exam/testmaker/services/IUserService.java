package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.User;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 21:50
 */
public interface IUserService {

    /**
     * Creates the specified user in the backend DB
     * @param user The user to be created.
     */
    void createUser(User user);

    User update(User user);
    /**
     * Checks if the given user name is already used by someone else.
     * This method is used when creating new users.
     * @param userName The user name to be checked
     * @return True if the user name can be found in the database; Otherwise false.
     */
    boolean isUserNameExists(String userName);

    /**
     * Check if the provided email exists in the DB. Email address should be unique in this system since it will be used for notification
     * and password related stuff
     * @param email
     * @return
     */
    boolean isEmailExists(String email);
    /**
     * Attempts a simple authentication check for the given combination of user name and password
     * @param userName user name
     * @param password password
     * @return <b>True</b> if authentication succeeds, otherwise false.
     */
    User authenticate(String userName, String password) throws LoginException;

    /**
     *
     * @param userName
     * @param password
     * @param requireAdmin
     * @throws LoginException
     */
    User authenticate(String userName, String password, boolean requireAdmin) throws LoginException;

    /**
     * Changes password for the given user.
     * @param userName THe user name whose password will be changed
     * @param oldPassword Old password
     * @param newPassword New password
     * @return <b>true</b> if password is changed successfully. <b>false</b> is returned if
     * <ul>
     *     <li>the old password is invalid</li>
     *     <li>or username does not exist</li>
     *     <li>or the new password is the same as the old one</li>
     * </ul>
     */
    boolean changePassword(String userName, String oldPassword, String newPassword);

    /**
     * Find the user with the given id.
     * @param id Unique user id
     * @return The User object for the given id
     */
    User findUser(int id);

    /**
     * Find the user with the given name.
     * @param userName The user name
     * @return The User object.
     */
    User findUser(String userName);

    /**
     * Get all users in the system
     * @return All users in the system.
     */
    List<User> findAllUsers();

    /**
     * Finds all users that are activated
     * @return all active users
     */
    List<User> findActivatedUsers();

    /**
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<User> findUsers(int pageSize, int pageNumber);

    int count();

    User findFirstUser();

    void updateLastLogin(String username, Date date);

    User createBuiltInUser();
}

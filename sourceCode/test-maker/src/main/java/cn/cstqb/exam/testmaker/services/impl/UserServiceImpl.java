package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.dao.UserDao;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IUserService;
import cn.cstqb.exam.testmaker.util.HashUtil;
import com.google.common.base.Strings;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 22:05
 */
public class UserServiceImpl implements IUserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserDao userDao;
    @Inject ApplicationConfigContext configContext;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Creates the specified user in the backend DB
     *
     * @param user The user to be created.
     */
    @Override
    public void createUser(User user) {
        checkArgument(user !=null && user.getUsername()!=null && user.getEmail()!=null,
                "Invalid user data. User name : %s", user);
        userDao.create(user);
    }

    /**
     * Checks if the given user name is already used by someone else.
     * This method is used when creating new users.
     *
     * @param username The user name to be checked
     * @return True if the user name can be found in the database; Otherwise false.
     */
    @Override
    public boolean isUserNameExists(String username) {
        checkArgument(!Strings.isNullOrEmpty(username), "The user name cannot be empty or null.");
        if (logger.isDebugEnabled()) {
            logger.debug("Checking if username [{}] exists.", username);
        }
        return userDao.checkUserNameExists(username);
    }

    /**
     * Check if the provided email exists in the DB. Email address should be unique in this system since it will be used for notification
     * and password related stuff
     *
     * @param email
     * @return
     */
    @Override
    public boolean isEmailExists(String email) {
        checkArgument(!Strings.isNullOrEmpty(email));
        return userDao.checkEmailExists(email);
    }

    /**
     * Changes password for the given user.
     *
     * @param userName    THe user name whose password will be changed
     * @param oldPassword Old password
     * @param newPassword New password
     * @return <b>true</b> if password is changed successfully. <b>false</b> is returned if
     * <ul>
     * <li>the old password is invalid</li>
     * <li>or username does not exist</li>
     * <li>or the new password is the same as the old one</li>
     * </ul>
     */
    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        checkArgument(userName != null && !userName.isEmpty(), "The user name cannot be empty or null.");
        if(oldPassword.equals(newPassword)) {
            logger.error("The new password is the same as the old one!");
            return false;
        }
        User persisted = findUser(userName);
        if (persisted == null) {
            logger.error("Username does not exist. {}", userName);
            return false;
        }
        if (!HashUtil.hash(oldPassword).equals(HashUtil.hash(oldPassword))) {
            logger.error("Wrong password for user [{}]",userName);
            return false;
        }

        persisted.setPassword(newPassword);
        userDao.update(persisted);
        return true;
    }

    /**
     * Attempts a simple authentication check for the given combination of user name and password
     *
     * @param userName user name
     * @param password password
     * @return <b>True</b> if authentication succeeds, otherwise false.
     */
    @Override
    public User authenticate(String userName, String password) throws LoginException {
        return authenticate(userName, password, false);
    }

    /**
     * @param userName
     * @param password
     * @param requireAdmin
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    public User authenticate(String userName, String password, boolean requireAdmin) throws LoginException {
        checkArgument(userName != null && !userName.isEmpty(), "The user name cannot be empty or null.");
        checkArgument(password != null && !password.isEmpty(), "The password cannot be empty or null.");
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to authenticate user [{}]", userName);
        }
        //user name not found in db
        if (!isUserNameExists(userName)) {
            String msg = String.format("User name not found: %s", userName);
            logger.error(msg);
            throw new AccountNotFoundException(msg);
        }

        //if the user provided password hash equals the hash in db, the authentication is thought to be successful.
        String hashed = HashUtil.hash(password);
        User user = findUser(userName);
        String persisted=user.getPassword();
        if (!hashed.equals(persisted)) {
            if (logger.isErrorEnabled()) {
                logger.error("Incorrect password for {}", userName);
            }
            throw new FailedLoginException("Invalid user name or password!");
        }

        if (logger.isInfoEnabled()) {
            logger.info("User name and password check passed!");
        }

        if (!user.isEnabled()) {
            logger.error("User is locked: {}", user.getUsername());
            throw new AccountLockedException("Account is locked!");
        }

        //check if the user is admin
        if (!user.isAdmin() && requireAdmin) {
            logger.error("User is not admin: {}", user.getUsername());
            throw new FailedLoginException("Unauthorized access. Admin access required.");
        }

        if (logger.isInfoEnabled()) {
            logger.info("Authentication passed for user: {}", user.getUsername());
        }
        user.setLastLogin(new Date());
        user.setLoginCount(user.getLoginCount()+1);
        userDao.update(user);
        return user;
    }

    /**
     * Find the user with the given id.
     *
     * @param id Unique user id
     * @return The User object for the given id
     */
    @Override
    public User findUser(int id) {
        checkArgument(id>0,"Invalid use id: %s",id);
        return userDao.findById(id);
    }

    /**
     * Find the user with the given name.
     *
     * @param userName The user name
     * @return The User object or <b>null</b> if not found.
     */
    @Override
    public User findUser(String userName) {
        checkArgument(userName != null && !userName.isEmpty(), "The user name cannot be empty or null.");
        if (!isUserNameExists(userName)) {
            logger.warn("User name not found in db: {}", userName);
            return null;
        }
        return userDao.findUser(userName);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    /**
     * Finds all users that are activated
     *
     * @return all active users
     */
    @Override
    public List<User> findActivatedUsers() {
        return userDao.findActiveUsers();
    }

    /**
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public List<User> findUsers(int pageSize, int pageNumber) {
        return userDao.findAll(pageSize, pageNumber);
    }

    @Override
    public int count() {
        return userDao.size().intValue();
    }

    @Override
    public User findFirstUser() {
        User firstUser = userDao.first();
        if (firstUser == null) {
            Config builtInUser=configContext.getConfig().getConfig("application.built-in.user");
            logger.debug("UserServiceImpl.findFirstUser: built -in: {}", builtInUser );
            firstUser = new User(builtInUser.getString("username"), builtInUser.getString("password"));
            logger.info("There is no user yet. Creating first user as admin. username={}, password=*****", firstUser.getUsername());
            firstUser.setEmail(builtInUser.getString("email"));
            firstUser.setPhone(builtInUser.getString("phone"));
            firstUser.setAdmin(true);
            firstUser.setFullName(builtInUser.getString("fullName"));
            userDao.create(firstUser);
            logger.info("Created user: {}", firstUser);
        }else if(!firstUser.isAdmin()){
            firstUser.setAdmin(true);
            userDao.update(firstUser);
            logger.debug("UserServiceImpl.findFirstUser: The first user will be the default system admin. User details: [{}]", firstUser);
        }
        return firstUser;
    }

    @Override
    public User createBuiltInUser() {
        User builtInUser=configContext.getBuiltInUser();
        userDao.create(builtInUser);
        return builtInUser;
    }

    @Override
    public User update(User user) {
        checkNotNull(user);
        checkArgument(user.getId()>0);
        logger.debug("UserServiceImpl.update: {}", user);
        return userDao.update(user);
    }

    @Override
    public void updateLastLogin(String username, Date date) {
        checkArgument(!Strings.isNullOrEmpty(username));
        checkNotNull(date);
        User user = findUser(username);
        user.setLastLogin(date);
        logger.debug("UserServiceImpl.updateLastLogin: Set user [{}] last login to {}", username, date );
        userDao.update(user);
    }
}

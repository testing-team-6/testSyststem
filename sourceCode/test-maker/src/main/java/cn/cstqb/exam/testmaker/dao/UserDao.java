package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 19:52
 */
public interface UserDao extends GenericDao<User, Integer> {
    List<String> getAllUserNames();

    /**
     * Finds the user with the provided user name from DB
     *
     * @param userName The user name to look for
     * @return The User object matching the user name or <i>null</i> if not found.
     */
    User findUser(String userName);

    /**
     * Check if the provided email exists in db or not.
     *
     * @param email cannot be null or empty
     * @return
     */
    boolean checkEmailExists(String email);

    List<User> findActiveUsers();

    /**
     * Check if the provided user name exists in the DB
     *
     * @param username
     * @return
     */
    boolean checkUserNameExists(String username);
}

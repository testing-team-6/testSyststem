package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.UserDao;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 19:52
 */
public class UserDaoImpl extends GenericJpaDaoImpl<User,Integer> implements UserDao {

    @Override
    @Transactional
    public List<String> getAllUserNames() {
        EntityManager em = provider.get();
        Query query = em.createNativeQuery(String.format("SELECT username FROM %s", tableName));
        return query.getResultList();
    }

    /**
     * Finds the user with the provided user name from DB
     *
     * @param userName The user name to look for
     * @return The User object matching the user name or <i>null</i> if not found.
     */
    @Override
    @Transactional
    public User findUser(String userName) {
        EntityManager em = provider.get();
        User user = null;
        try {
            user=em.createNamedQuery("User.findByUserName", User.class)
                .setParameter("username", userName)
            .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("User not found for {}", userName);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("User details for [{}] : {}", userName, user);
        }
        return user;
    }

    @Override
    @Transactional
    public List<User> findActiveUsers() {
        EntityManager em = provider.get();
//        TypedQuery<User> query = em.createQuery("SELECT u FROM User u LEFT OUTER JOIN FETCH u.projects WHERE u.enabled =:enabled ORDER BY u.id ", User.class);
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.enabled =:enabled ORDER BY u.id ", User.class);
        return query
                .setParameter("enabled", true)
                .getResultList();
    }

    /**
     * Check if the provided email exists in db or not.
     *
     * @param email cannot be null or empty
     * @return
     */
    @Override
    @Transactional
    public boolean checkEmailExists(String email) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(email),"Email address to check cannot be null/empty!");
        EntityManager em = provider.get();
        Query query = em.createNativeQuery("SELECT EXISTS (SELECT id FROM User u WHERE u.email =:email) ");
        query.setParameter("email", email);
        Number result= (Number) query.getSingleResult();
        logger.debug("UserDaoImpl.checkEmailExists: return value from DB: {}", result );
        return result.intValue() != 0;
    }

    @Override
    @Transactional
    public boolean checkUserNameExists(String username) {
        EntityManager em = provider.get();
        TypedQuery<Long> query = em.createQuery("SELECT count(u) FROM User u WHERE u.username =:username ", Long.class);
        query.setParameter("username", username);
        Long result= query.getSingleResult();
        logger.debug("UserDaoImpl.checkUserNameExists: return value from DB: {}", result );
        return result > 0;
    }
}

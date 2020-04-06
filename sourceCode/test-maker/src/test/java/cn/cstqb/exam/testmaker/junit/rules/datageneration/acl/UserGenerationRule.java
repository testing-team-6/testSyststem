package cn.cstqb.exam.testmaker.junit.rules.datageneration.acl;

import cn.cstqb.exam.testmaker.dao.UserDao;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.AbstractDataGenerationRule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public class UserGenerationRule extends AbstractDataGenerationRule {
    protected UserDao userDao;

    public UserGenerationRule() {
        super();
    }

    public void populate() {
        createUsers();
    }

    @Override
    protected void init() {
        userDao = injector.getInstance(UserDao.class);
    }

    protected void createUsers() {
        for (int i = 0; i < dataCount; i++) {
            User user = new User(String.format("user-%d_%d", i, System.currentTimeMillis()));
            userDao.create(user);
        }
    }


}

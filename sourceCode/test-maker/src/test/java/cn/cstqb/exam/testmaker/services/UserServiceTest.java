package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.util.HashUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {
    private IUserService service;
    
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @Before
    public void setUp() throws Exception {
        service = rule.getInjector().getInstance(IUserService.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User("david"+System.currentTimeMillis());
        newUser.setEmail("gao.jianmin@qq.com");
        service.createUser(newUser);

        assertTrue(newUser.getId()>0);
    }

    @Test
    public void testIsUserNameExists() throws Exception {
        String userName = "david";
        boolean isExists = service.isUserNameExists(userName);
        assertTrue(isExists);
    }

    @Test
    public void testChangePassword() throws Exception {
        String userName = "test-user-" + System.currentTimeMillis();
        String passwd = "test";
        User tmp = new User(userName);
        tmp.setEmail("abc@foo.com");
        tmp.setPassword(passwd);
        service.createUser(tmp);

        String newPasswd = "test123";
        String newPasswdHashed = HashUtil.hash(newPasswd);
        service.changePassword(userName, passwd, newPasswd);

        User persisted = service.findUser(userName);

        System.out.printf("New password: [%s]", newPasswdHashed);
        assertNotEquals("Changing password failed.", HashUtil.hash(passwd),persisted.getPassword());
    }

    @Test
    public void testValidateLogin() throws Exception {
        String userName = "david";
        String password = "test123";
        service.authenticate(userName, password);
    }

    @Test
    public void testFindUser() throws Exception {
        User user=service.findUser("david");
        assertNotNull(user);
    }

    @Test
    public void testFindUser1() throws Exception {
        User user=service.findUser(1);
        assertNotNull(user);
    }
}
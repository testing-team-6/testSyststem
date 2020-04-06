package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.DefaultGenerationRule;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class UserDaoTest {
    private UserDao dao;
    private ProjectDao projectDao;
    @ClassRule
    public static DefaultGenerationRule rule =new DefaultGenerationRule();

    @Before
    public void setUp() {
        dao = rule.getInjector().getInstance(UserDao.class);
        projectDao = rule.getInjector().getInstance(ProjectDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 5; i++) {
            User user = new User(String.format("user-%d-%d", i, System.currentTimeMillis()));
            user.setEmail(String.format("test-%s@bar.com", System.currentTimeMillis()));
            user.setPassword("test123");
            dao.create(user);
            assertTrue(user.getId() > 0);
        }
    }

    @Test
    public void testGetProjects() throws Exception {
        User user = dao.findById(1);
        System.out.printf("User details: %s\n", user);
        assertTrue(user.getProjects() != null && !user.getProjects().isEmpty());
        for (Project project : user.getProjects()) {
            System.out.printf("PROJECT FOUND: %s\n", project.getName());
        }
    }

    @Test
    public void testCheckUserNameExists() throws Exception {
        String username = "sa";
        boolean result = dao.checkUserNameExists(username);
        assertTrue(result);
    }

    @Test
    public void testCheckUserNameExistsFalse() throws Exception {
        String username = "oosososososos";
        boolean result = dao.checkUserNameExists(username);
        assertFalse(result);
    }

    @Test
    public void testCheckEmailExists() throws Exception {
        String email = "test@test.com";
        boolean exist = dao.checkEmailExists(email);
        assertTrue(exist);
    }
    @Test
    public void testCheckEmailExistsFalse() throws Exception {
        String email = "test@usa.com";
        boolean exist = dao.checkEmailExists(email);
        System.out.printf("Exists: %s\n",exist);
        assertFalse(exist);
    }

    @Test
    public void testSetNewProjects() throws Exception {
        User user = dao.findById(2);

        List<Project> projects = projectDao.findAll();
        if (user.getProjects() != null) {
            user.getProjects().addAll(projects);
        }
        dao.update(user);
        assertFalse(dao.findById(2).getProjects().isEmpty());
    }

    @Test
    public void testSetWithExistingProjects() throws Exception {
        User user = dao.findById(2);
        List<Project> existing = user.getProjects();
        assertNotNull(existing);//the user should have existing projects

        List<Project> toBeAssigned = projectDao.findAll();

        for (Project project : toBeAssigned) {
            project.addUser(user);
            projectDao.update(project);
        }
    }

    @Test
    public void testUpdate() {
        User user = dao.findById(dao.getMinID());
        user.setEmail("test@abc.com."+System.currentTimeMillis());
        user.setEnabled(true);
        user.setPassword("test");
        dao.update(user);
    }

    @Test
    public void testGetAll(){
        List<User> users=dao.findAll();
        assertNotNull(users);
        System.out.println(users);
    }

    @Test
    public void testDelete(){
        Long size=dao.size();
        int lastId=dao.getMaxID();
        dao.delete(dao.findById(lastId));
        assertEquals(size-1, (long)dao.size());
    }

    @Test
    public void testGetUserNames(){
        List<String> names=dao.getAllUserNames();
        assertNotNull(names);
        for (String name : names) {
            System.out.printf("username: %s\n", name);
        }
    }

    @Test
    public void testFindAllPaginated(){
        int pageSize=10;
        int pageNumber=2;
        List<User> users = dao.findAll(pageSize, pageNumber);
        assertNotNull(users);
    }

    @Test
    public void testFindByd() throws Exception {
        User user = dao.findById(2);
        assertNotNull(user);
    }

    @Test
    public void testFindActiveUsers() throws Exception {
        List<User> users = dao.findActiveUsers();
        assertTrue(users!=null && !users.isEmpty());
        for (User user : users) {

            System.out.printf("%s\n", user);
        }
    }
}

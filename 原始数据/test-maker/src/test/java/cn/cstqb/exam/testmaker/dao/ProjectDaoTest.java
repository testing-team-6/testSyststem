package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.acl.UserGenerationRule;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.project.StatusGenerationRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class ProjectDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ProjectDao dao;
    private static UserDao userDao;

    @ClassRule
    public static StatusGenerationRule jpaRule=new StatusGenerationRule();


    @Before
    public void setUp() {
        userDao = jpaRule.getInjector().getInstance(UserDao.class);
        dao = jpaRule.getInjector().getInstance(ProjectDao.class);
    }

    @Test
    public void testCreate() {
        Integer id= userDao.getMinID();
        User user= userDao.findById(id);
        int statusId=jpaRule.getProjectStatusDao().getMinID();
        ProjectStatus status = jpaRule.getProjectStatusDao().findById(statusId);
        for (int i = 0; i < jpaRule.getDataCount(); i++) {
            Project project = new Project("Test Making for CSTQB-FL. "+System.currentTimeMillis(), user,status);
            dao.create(project);
            int dbID=project.getId();
            System.out.printf("Project saved with id: %d\n",dbID);
            assertTrue(dbID > 0);
        }
    }

    @Test
    public void testFindById() throws Exception {
        Project project = dao.findById(151);
        assertNotNull(project);
        System.out.printf("Located Project: %s\n", project);
    }

    @Test
    public void testUpdate() {
        Project project = dao.findById(dao.getMinID());
        project.setStartDate(new Date());
        User user = userDao.findById(2);
        List<User> users = Lists.newArrayList(user);

        logger.info("Adding {} to project {}", user.getUsername(), project.getName());
        project.setUsers(users);
        dao.update(project);
    }

    @Test
    public void testGetAll(){
        List<Project> projects= dao.findAll();
        assertNotNull(projects);
        for (Project project : projects) {
            System.out.printf("Project: %s\n",project);
        }
    }

    @Test
    public void testDelete(){
        int last=dao.getMaxID();
        System.out.printf("About to delete: %d\n", last);
        dao.delete(dao.findById(last));
        ApplicationConfigContext context = ApplicationConfigContext.getInstance();
    }

    @Test
    public void testSetUsers() throws Exception {
        List<User> users=userDao.findAll();
        Project project=dao.first();
        project.setUsers(users);
        dao.update(project);
        assertTrue(project.getUsers()!=null && !project.getUsers().isEmpty());
    }

    @Test
    public void testAddUser() throws Exception {
        Project project = dao.findById(2);
        User user = userDao.findById(2);
        project.addUser(user);
        dao.update(project);
        assertTrue(project.getUsers().size() > 0);
    }

    @Test
    public void testGetUsers() throws Exception {
        Project project=dao.first();
        assertTrue(project.getUsers()!=null && !project.getUsers().isEmpty());
    }

}

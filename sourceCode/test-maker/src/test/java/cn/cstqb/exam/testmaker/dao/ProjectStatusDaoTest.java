package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * ProjectStatus: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class ProjectStatusDaoTest {
    private ProjectStatusDao dao;

    @ClassRule
    public static AbstractJpaRule jpaRule=new DefaultJpaRule();

    @Before
    public void setUp() {
        dao = jpaRule.getInjector().getInstance(ProjectStatusDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 5; i++) {

            ProjectStatus sa =new ProjectStatus(String.format("Dummy Status-%d.%d",i,System.currentTimeMillis()));
            dao.create(sa);
            assertTrue(sa.getId() > 0);
        }
    }

    @Test
    public void testUpdate() {
        ProjectStatus status = dao.findById(dao.getMinID());
        status.setName(status.getName()+"@"+System.currentTimeMillis());
        dao.update(status);
    }

    @Test
    public void testGetAll(){
        List<ProjectStatus> statuses=dao.findAll();
        assertNotNull(statuses);
        for (ProjectStatus status : statuses) {
            System.out.printf("Status: %s\n", status);
        }
    }

    @Test
    public void testDelete(){
        int lastId=dao.getMaxID();
        System.out.printf("About to delete status #%d\n", lastId);
        dao.delete(dao.findById(lastId));
    }

    @Test
    public void testCanAddStart() throws Exception {
        boolean canAdd=dao.canAddStartStatus();

        if (dao.findResultList("isStart", true).size() > 0) {
            assertFalse(canAdd);
        } else {
            assertTrue(canAdd);
        }
    }
}

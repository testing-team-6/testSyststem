package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Role;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
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
public class RoleDaoTest {
    private RoleDao dao;

    @ClassRule
    public static AbstractJpaRule jpaRule=new DefaultJpaRule();

    @Before
    public void setUp() {
        dao = jpaRule.getInjector().getInstance(RoleDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 5; i++) {
            Role sa =new Role(String.format("role-%d-%d",i,System.currentTimeMillis()));
            dao.create(sa);
            assertTrue(sa.getId() > 0);
        }
    }

    @Test
    public void testUpdate() {
        Role role = dao.findById(dao.getMinID());
        role.setName("test-role." + System.currentTimeMillis());
        dao.update(role);
    }

    @Test
    public void testDelete(){
        Long size=dao.size();
        int lastId=dao.getMaxID();
        dao.delete(dao.findById(lastId));
        assertEquals( (size-1), (long)dao.size());
    }

}

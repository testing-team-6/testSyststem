package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionType;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionTypeDaoTest {
    private QuestionTypeDao dao;

    @ClassRule
    public static DefaultJpaRule jpaRule=new DefaultJpaRule();

    @Before
    public void setUp() {
        dao = jpaRule.getInjector().getInstance(QuestionTypeDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 5; i++) {
            QuestionType type = new QuestionType(String.format("Status-%d-%d",i,System.currentTimeMillis()));
            dao.create(type);
            assertTrue(type.getId() > 0);
            System.out.printf("%s saved with ID: %d", type.getClass().getSimpleName(), type.getId());
        }
    }

    @Test
    public void testUpdate() {
        QuestionType status = dao.findById(dao.getMinID());
        status.setName("Updated status-" + System.currentTimeMillis());
        dao.update(status);
    }

    @Test
    public void testDelete(){
        int lastId=dao.getMaxID();
        QuestionType type = dao.findById(lastId);
        dao.delete(type);
        assertNull(dao.findById(lastId));
    }
}

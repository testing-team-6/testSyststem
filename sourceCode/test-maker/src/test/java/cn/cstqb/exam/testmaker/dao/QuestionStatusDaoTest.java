package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.DefaultGenerationRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionStatusDaoTest {
    private QuestionStatusDao dao;

    @ClassRule
    public static DefaultGenerationRule rule =new DefaultGenerationRule();

    @Before
    public void setUp() {
        dao = rule.getInjector().getInstance(QuestionStatusDao.class);
        prepare();
    }

    @Test
    public void testCreate() {
        assertTrue(dao.size()>=rule.getDataCount());
    }

    @Test
    public void testUpdate() {
        QuestionStatus status = dao.last();
        status.setAccessibleByFacilitator(true);
        status.setName("UpdatedStatus @" + System.currentTimeMillis());
        dao.update(status);
    }

    @Test
    public void testFindStart() throws Exception {
        List<QuestionStatus> status = dao.findResultList("isStart", true);
        assertNotNull(status);
        for (QuestionStatus statu : status) {
            System.out.printf("Question Status:%s\n", statu);
        }
    }

    @Test
    public void testGetAll(){
        List<QuestionStatus> statuses=dao.findAll();
        assertTrue(statuses.size()>= rule.getDataCount());
        System.out.println(statuses);
    }

    @Test
    public void testGetById() {
        QuestionStatus status = dao.findById(dao.getMinID());
        assertNotNull(status);
        System.out.println(status);
    }

    @Test
    public void testMinID(){
        Integer id=dao.getMinID();
        assertNotNull(id);
    }
    @Test
    public void testMaxID() {
        dao.create(new QuestionStatus("zh-CN"+System.currentTimeMillis()));
        Integer id = dao.getMaxID();
        System.out.printf("Max id: %d\n",id);
        assertNotNull(id);
    }

    @Test
    public void testDelete() {
        int statusId = dao.getMaxID();
        QuestionStatus status = dao.findById(statusId);
        dao.delete(status);
        assertNull(dao.findById(statusId));
    }
    private void prepare() {
        int gap = (int) (rule.getDataCount() - dao.size());
        if (dao.size() < rule.getDataCount()) {
            for (int i = 0; i < gap; i++) {
                _create(i);
            }
        }
    }

    private void _create(int index) {
        QuestionStatus status = new QuestionStatus(String.format("Question Status #%d @%d", index, System.currentTimeMillis()));
        dao.create(status);
        assertTrue(status.getId() > 0);
    }
}

package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransition;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransitionPK;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.DefaultGenerationRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionStatusTransitionDaoTest {
    private QuestionStatusDao questionStatusDao;
    private QuestionStatusTransitionDao dao;

    @ClassRule
    public static DefaultGenerationRule rule =new DefaultGenerationRule();

    @Before
    public void setUp() {
        questionStatusDao = rule.getInjector().getInstance(QuestionStatusDao.class);
        dao = rule.getInjector().getInstance(QuestionStatusTransitionDao.class);
        prepare();
    }

    @Test
    public void testCreate() {
        assertTrue(dao.size()>=rule.getDataCount()-1);
    }

    @Test
    public void testFindTransitions() throws Exception {
        QuestionStatus status=questionStatusDao.findById(5);
        List<QuestionStatusTransition> transitions = dao.findTransitions(status);
        for (QuestionStatusTransition transition : transitions) {
            System.out.printf("Transition: %s\n", transition);
        }
        assertTrue(transitions.size()>0);
    }

    @Test
    public void testUpdate() {
        int statusId = questionStatusDao.getMinID();
        QuestionStatusTransitionPK pk = new QuestionStatusTransitionPK(questionStatusDao.first(), questionStatusDao.last());
        QuestionStatusTransition transition = dao.findById(pk);
        transition.setSequence(99999);
        dao.update(transition);
    }

    @Test
    public void testGetAll(){
        List<QuestionStatusTransition> statuses= dao.findAll();
        assertTrue(statuses.size()>= rule.getDataCount());
        System.out.println(statuses);
    }

    @Test
    public void testGetById() {
        QuestionStatus status = questionStatusDao.findById(questionStatusDao.getMinID() + 1);
        QuestionStatusTransitionPK pk = new QuestionStatusTransitionPK(status, questionStatusDao.last());
        QuestionStatusTransition transition = dao.findById(pk);
        assertNotNull(transition);
        System.out.println(transition);
    }


    @Test
    public void testDelete() {
        QuestionStatus status = questionStatusDao.findById(questionStatusDao.getMinID() + 1);
//        QuestionStatus status = questionStatusDao.findById(questionStatusDao.getMinID() + 1);
        QuestionStatusTransitionPK pk = new QuestionStatusTransitionPK(status, status);
        QuestionStatusTransition transition = dao.findById(pk);
        dao.delete(transition);
        //FIXME unable to delete due to EntityManager for each method
        //fixed by removing Transactional annotation for findById method
        assertNull(dao.findById(pk));
    }
    private void prepare() {
        int statusGap= (int) (rule.getDataCount() - questionStatusDao.size());
        for (int i = 0; i <statusGap; i++) {
            QuestionStatus status = new QuestionStatus(String.format("Question Status %d @%d", i, System.currentTimeMillis()));
            questionStatusDao.create(status);
        }

        int gap = (int) (rule.getDataCount() - dao.size());
            for (int i = 0; i < gap; i++) {
                _create(i+1);
            }
    }

    private void _create(int index) {
        QuestionStatus status = questionStatusDao.findById( questionStatusDao.getMinID() + index);
        if (status == null) {
            return;
        }
        for (int i = 0; i < rule.getDataCount(); i++) {
            int nextId = index + i + 1;
            if (nextId <=rule.getDataCount()) {
                QuestionStatus next = questionStatusDao.findById(nextId);
                if (next == null) {
                    continue;
                }

                QuestionStatusTransitionPK pk = new QuestionStatusTransitionPK(status,next);
                QuestionStatusTransition transition = new QuestionStatusTransition(pk, nextId);
                dao.create(transition);
            }
        }
    }

    @Test
    public void testExistsTransition() throws Exception {
        QuestionStatus status = questionStatusDao.findById(1);
        QuestionStatus nextStatus = questionStatusDao.findById(2);
        boolean exists = dao.existsTransition(status, nextStatus);
        assertTrue(exists);
    }
    @Test
    public void testExistsTransitionFalse() throws Exception {
        QuestionStatus status = questionStatusDao.findById(1);
        boolean exists = dao.existsTransition(status, status);
        assertFalse(exists);
    }

    @Test
    public void testFindTransitionalStatus() throws Exception {
        Set<QuestionStatus> statuses = dao.findTransitionalStatus(null);
        assertTrue(statuses!=null && !statuses.isEmpty());

    }

    @Test
    public void testSaveTransitions() throws Exception {
        QuestionStatus status = questionStatusDao.findById(11);
        List<QuestionStatus> statuses = questionStatusDao.findAll();

        dao.saveTransitionalStates(status, new HashSet<>(statuses));
    }

    @Test
    public void testDeleteTransitionalStates() throws Exception {
        QuestionStatus status = questionStatusDao.findById(11);
        List<QuestionStatus> statuses = new ArrayList<>();
        for (int i = 11; i <= questionStatusDao.getMaxID(); i++) {
            statuses.add(questionStatusDao.findById(i));
        }

        dao.deleteTransitionalStates(status, new HashSet<>(statuses));
    }

    @Test
    public void testDeleteTransitions() throws Exception {
        QuestionStatus status = questionStatusDao.findById(19);
        dao.deleteTransitions(status);
    }

}

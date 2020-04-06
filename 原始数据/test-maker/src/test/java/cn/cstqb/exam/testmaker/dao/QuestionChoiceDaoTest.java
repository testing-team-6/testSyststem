package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.QuestionElementPreparationRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionChoiceDaoTest {
    private QuestionDao questionDao;
    private QuestionChoiceDao dao;

    @ClassRule
//    public static QuestionElementPreparationRule rule =new QuestionElementPreparationRule();
    public static DefaultJpaRule rule =new DefaultJpaRule();


    @Before
    public void setUp() {
        dao = rule.getInjector().getInstance(QuestionChoiceDao.class);
        questionDao = rule.getInjector().getInstance(QuestionDao.class);
    }

/*    @Test
    public void testCreate() {
        int questionId = questionDao.getMaxID();
        Question question = questionDao.findById(questionId);
        for (int i = 0; i < rule.getDataCount(); i++) {
            _create(question, i);
        }
    }*/

/*    @Test
    public void testUpdate() {
        prepareForNonCreateTests();
        Question question = rule.getQuestionDao().findById(rule.getQuestionDao().getMaxID());
        _create(question, new Random().nextInt());

        QuestionChoice choice = dao.findById(dao.getMaxID());
        choice.setIsCorrectAnswer(true);
        dao.update(choice);
    }*/

    @Test
    public void testDelete() {
//        prepareForNonCreateTests();
        int lastId=dao.getMaxID();
        System.out.printf("Last id of the entity: %d\n", lastId);
        QuestionChoice point = dao.findById(lastId);
        dao.delete(point);
    }

    @Test
    public void testDeleteFail() throws Exception {
//        QuestionChoice choice=dao.findById()
        QuestionChoice choice=new QuestionChoice();
        dao.delete(choice);
    }

    private void _create(Question question, int index) {
        QuestionChoice choice = new QuestionChoice(question,
                String.format("Question choice #%d @%d", index, System.currentTimeMillis()));
        dao.create(choice);
    }

/*    private void prepareForNonCreateTests() {
        if (dao.size() < rule.getDataCount()) {
            testCreate();
        }
    }*/


}

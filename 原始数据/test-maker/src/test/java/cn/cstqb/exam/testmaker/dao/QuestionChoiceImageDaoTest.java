package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionChoiceImageDaoTest {
    private QuestionDao questionDao;
    private QuestionChoiceDao choiceDao;
    private QuestionChoiceImageDao dao;

    @ClassRule
    public static DefaultJpaRule rule =new DefaultJpaRule();


    @Before
    public void setUp() {
        dao = rule.getInjector().getInstance(QuestionChoiceImageDao.class);
        choiceDao = rule.getInjector().getInstance(QuestionChoiceDao.class);
        questionDao = rule.getInjector().getInstance(QuestionDao.class);
    }

/*    @Test
    public void testCreate() {
        prepare();
        QuestionChoice choice = choiceDao.last();
        for (int i = 0; i < rule.getDataCount(); i++) {
            QuestionChoiceImage choiceImage = new QuestionChoiceImage(choice,
                    System.getProperty("user.home")+"\\"+System.currentTimeMillis());
            dao.create(choiceImage);
        }
    }

    private void prepare() {
        Question question = questionDao.last();

        for (int i = 0; i < rule.getDataCount(); i++) {
            String content=String.format("Question choice content: %d", System.currentTimeMillis());
            QuestionChoice choice = new QuestionChoice(question,Character.forDigit(65+i,10),content);
            choiceDao.create(choice);
        }
    }*/

    @Test
    public void testFindById() throws Exception {


    }

    @Test
    public void testList() throws Exception {
        QuestionChoice choice = choiceDao.findById(12);
        List<QuestionChoiceImage> images = dao.findResultList("choice", choice);
        assertTrue(images!=null && !images.isEmpty());
        for (QuestionChoiceImage image : images) {
            System.out.println(image);
        }
    }
}

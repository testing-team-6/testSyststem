package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.QuestionElementPreparationRule;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.QuestionPreparationRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class ReviewCommentDaoTest {
    private ProjectDao projectDao;
    private UserDao userDao;
    private QuestionDao questionDao;
    private QuestionTypeDao questionTypeDao;
    private QuestionLanguageDao questionLanguageDao;
    private QuestionStatusDao statusDao;
    private KnowledgePointDao knowledgePointDao;
    private ReviewCommentDao dao;

    @ClassRule
    public static QuestionElementPreparationRule rule =new QuestionElementPreparationRule();


    @Before
    public void setUp() {
        dao = rule.getInjector().getInstance(ReviewCommentDao.class);
        projectDao = rule.getInjector().getInstance(ProjectDao.class);
        userDao = rule.getInjector().getInstance(UserDao.class);
        questionDao = rule.getInjector().getInstance(QuestionDao.class);
        questionTypeDao = rule.getInjector().getInstance(QuestionTypeDao.class);
        questionLanguageDao = rule.getInjector().getInstance(QuestionLanguageDao.class);
        statusDao = rule.getInjector().getInstance(QuestionStatusDao.class);
        knowledgePointDao = rule.getInjector().getInstance(KnowledgePointDao.class);
    }

    @Test
    public void testCreate() {
        int firstQuestion = questionDao.getMaxID();
        Question question = questionDao.findById(firstQuestion);
        for (int i = 0; i < rule.getDataCount(); i++) {
            User user = userDao.findById(userDao.getMinID() + i);
            createComment(question, i, user);
        }
    }

    private void createComment(Question question,int index, User user) {
        ReviewComment comment = new ReviewComment(question,
                String.format("Question review comment #%d @%d", index, System.currentTimeMillis()),
                user);
        dao.create(comment);
    }

    @Test
    public void testUpdate() {
        Question question = rule.getQuestionDao().findById(rule.getQuestionDao().getMaxID());
        User reviewer = userDao.findById(userDao.getMaxID());
        createComment(question, new Random().nextInt(), reviewer);

        ReviewComment comment = dao.findById(dao.getMaxID());
        comment.setFinalReview(true);

        dao.update(comment);
    }

    @Test
    public void testDelete() {
        int lastId=dao.getMaxID();

        if (lastId<0) {
            testCreate();
            lastId=dao.getMaxID();
        }
        System.out.printf("Last id of the entity: %d\n", lastId);
        ReviewComment point = dao.findById(lastId);
        dao.delete(point);
    }

}

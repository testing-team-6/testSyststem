package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.QuestionPreparationRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuestionImageDaoTest {
    private QuestionImageDao dao;
    private QuestionDao questionDao;
    private ProjectDao projectDao;
    private UserDao userDao;
    private QuestionTypeDao questionTypeDao;
    private QuestionLanguageDao questionLanguageDao;
    private QuestionStatusDao statusDao;
    private KnowledgePointDao knowledgePointDao;
    @ClassRule
    public static QuestionPreparationRule rule =new QuestionPreparationRule();


    @Before
    public void setUp() throws Exception {
        dao = rule.getInjector().getInstance(QuestionImageDao.class);
        questionDao = rule.getInjector().getInstance(QuestionDao.class);
        projectDao = rule.getInjector().getInstance(ProjectDao.class);
        userDao = rule.getInjector().getInstance(UserDao.class);
        questionTypeDao = rule.getInjector().getInstance(QuestionTypeDao.class);
        questionLanguageDao = rule.getInjector().getInstance(QuestionLanguageDao.class);
        statusDao = rule.getInjector().getInstance(QuestionStatusDao.class);
        knowledgePointDao = rule.getInjector().getInstance(KnowledgePointDao.class);

    }

    @Test
    public void testCreate() throws Exception {
        prepare();
        Question question = questionDao.last();
        assertNotNull(question);
        System.out.printf("Last Question: %s\n", question);
        for (int i = 0; i < rule.getDataCount(); i++) {
            QuestionImage image = new QuestionImage(question,
                    System.currentTimeMillis()+"",
                    System.getProperty("user.home")+"\\"+System.currentTimeMillis());
            dao.create(image);
            assertTrue(image.getId()>0);
        }

    }

    private void prepare() {
        int firstProject=projectDao.getMinID();
        for (int i = 0; i < rule.getDataCount(); i++) {
            Project project = projectDao.findById(firstProject + i);
            User user = userDao.findById(userDao.getMinID() + i);
            QuestionType type = questionTypeDao.findById(questionTypeDao.getMinID() + i);
            QuestionLanguage language = questionLanguageDao.findById(questionLanguageDao.getMinID() + i);
            QuestionStatus status = statusDao.findById(statusDao.getMinID() + i);

            String stem = "This is the question stem @" + System.currentTimeMillis();
            Question question = new Question(stem, 3, language, type,
                    (short)(3 + i), project, user);
            question.setKnowledgePoint(knowledgePointDao.findById(knowledgePointDao.getMaxID() - i));
            question.setQualityAdmin(user);
            question.setStatus(status);
            question.setAuthoringStartDate(new Date());
            question.setAuthoringFinishDate(new Date());

            questionDao.create(question);
        }
    }
}

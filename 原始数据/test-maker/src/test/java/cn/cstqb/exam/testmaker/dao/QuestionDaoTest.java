package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Inject private QuestionDao dao;
    @Inject private ProjectDao projectDao;
    @Inject private UserDao userDao;
    @Inject private QuestionTypeDao questionTypeDao;
    @Inject private QuestionLanguageDao questionLanguageDao;
    @Inject private QuestionStatusDao statusDao;
    @Inject private KnowledgePointDao knowledgePointDao;
    @Inject private QuestionChoiceDao choiceDao;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @ClassRule
//    public static QuestionPreparationRule rule =new QuestionPreparationRule();
    public static DefaultJpaRule rule =new DefaultJpaRule();


    @Before
    public void setUp() throws ParseException {
        rule.getInjector().injectMembers(this);
    }

    @Test
    public void testCreate() throws ParseException {
        int firstProject=projectDao.getMinID();
/*        for (int i = 0; i < 5; i++) {
            Project project = projectDao.findById(firstProject + i);
            User user = userDao.findById(userDao.getMinID() + i);
            QuestionType type = questionTypeDao.findById(questionTypeDao.getMinID() + i);
            QuestionLanguage language = questionLanguageDao.findById(questionLanguageDao.getMinID() + i);
            QuestionStatus status = statusDao.findById(statusDao.getMinID() + i);
            Set<KnowledgePoint> points = new HashSet<>();
            points.add(knowledgePointDao.findById(knowledgePointDao.getMaxID() - i));

            String stem = "This is the question stem @" + System.currentTimeMillis();
            Question question = new Question(stem, 3, language, type,
                    (short)(3 + i), project, user);
            question.setKnowledgePoints(points);
            question.setQualityAdmin(user);
            question.setStatus(status);
            question.setAuthoringStartDate(new Date());
            question.setAuthoringFinishDate(new Date());

            dao.create(question);
        }*/

        Question question = new Question();
        Project project = projectDao.first();
        KnowledgePoint knowledgePoint = knowledgePointDao.first();
        QuestionType type=questionTypeDao.first();
        QuestionLanguage language=questionLanguageDao.first();
        User author = userDao.first();
        User qa=userDao.last();

        question.setProject(project);
        question.setKnowledgePoint(knowledgePoint);
        question.setType(type);
        question.setLanguage(language);
        question.setAuthor(author);
        question.setAuthoringStartDate(new Date());
        question.setAuthoringFinishDate(dateFormat.parse("2015-09-03"));
        question.setScore(knowledgePoint.getScore());
        question.setStatus(statusDao.findStart());
        question.setQualityAdmin(qa);
        question.setReviewers(Sets.newHashSet(qa));

        dao.create(question);
        assertTrue(question.isValidID());
    }

    @Test
    public void testUpdate() {
        Question question = dao.findById(4);
        question.setScore((short) 3);
        Question updated=dao.update(question);
        System.out.println(updated);
    }

    @Test
    public void testDelete() {
        int lastId=dao.getMaxID();
        Question question = dao.findById(lastId);
        if (logger.isDebugEnabled()) {
            logger.debug("Deleting question #{}. Details: {}", lastId, question);
        }
        dao.delete(question);
    }

    @Test
    public void testDifficultyLabel() {
        Question question = dao.findById(dao.getMaxID());
        assertNotNull(question.getDifficultyLabel());
        System.out.printf("Difficulty label: %s\n", question.getDifficultyLabel());
        System.out.println(question);
    }
}

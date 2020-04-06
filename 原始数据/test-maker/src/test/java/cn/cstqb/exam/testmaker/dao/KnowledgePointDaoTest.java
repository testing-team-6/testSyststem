package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

import com.google.inject.Inject;
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
public class KnowledgePointDaoTest {
    @Inject ChapterDao chapterDao;
    @Inject private KnowledgePointDao dao;

    @ClassRule
    public static AbstractJpaRule rule =new DefaultJpaRule();

    @Before
    public void setUp() {
        rule.getInjector().injectMembers(this);
    }

    @Test
    public void testCreate() {
        int chapterId= chapterDao.getMinID();
        System.out.printf("Min id: %d", chapterId);
        Chapter chapter = chapterDao.findById(chapterId);
        for (int i = 0; i < 5; i++) {

            KnowledgePoint point = new KnowledgePoint("3.5.1", "举例说明正式评审的特点"+System.currentTimeMillis(), "K2", (short) i,chapter);
            dao.create(point);
            int dbID = point.getId();
            System.out.printf("%s saved with id: %d\n", KnowledgePoint.class.getName(), dbID);
            assertTrue(dbID > 0);
        }
    }

    @Test
    public void testUpdate() {
        KnowledgePoint point = dao.findById(dao.getMinID());
        point.setkLevel("K-ssss");
        point.setChapter(chapterDao.findById(chapterDao.getMaxID()));
        dao.update(point);
    }

    @Test
    public void testDelete() {
        int lastId=dao.getMaxID();
        System.out.printf("Last id of the entity: %d\n", lastId);
        KnowledgePoint point = dao.findById(lastId);
        dao.delete(point);
    }

//    @Test
//    public void testFindKnowledgePoints() {
//    	Chapter chapter = chapterDao.findById(chapterDao.getMinID());
//    	List<KnowledgePoint> knowledgePoints = dao.findKnowledgePoint(chapter);
//    	assertNotNull(knowledgePoints);
//    	for (KnowledgePoint knowledgePoint : knowledgePoints) {
//    		System.out.printf("knowledge point title : %s", knowledgePoint.getTitle());
//    	}
//    }


    @Test
    public void testFindUsingQuestions() throws Exception {
        KnowledgePoint point = dao.findKnowledgePoint(11);
        System.out.println(point);
        List<Question> questions = dao.findUsingQuestions(point);
        assertTrue(questions!=null && !questions.isEmpty());
        for (Question question : questions) {
            System.out.printf("Question #%d is using the knowledge point\n", question.getId());
        }
    }
}

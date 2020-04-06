package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.ReviewAssessment;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.DefaultGenerationRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/20
 * Time: 20:48
 */
public class ReviewAssessmentDaoTest {
    private ReviewAssessmentDao dao;

    @ClassRule
    public static DefaultGenerationRule rule = new DefaultGenerationRule();

    @Before
    public void setUp() throws Exception {
        dao = rule.getInjector().getInstance(ReviewAssessmentDao.class);
    }

    @Test
    public void testCreate() throws Exception {
        for (int i = 0; i < rule.getDataCount(); i++) {
            ReviewAssessment assessment = new ReviewAssessment(String.format("ReviewAssessment@%d", System.currentTimeMillis()));
            dao.create(assessment);
        }
    }

    @Test
    public void testFindAll() throws Exception {
        List<ReviewAssessment> records = dao.findAll();
        assertTrue(records != null && !records.isEmpty());
    }
}

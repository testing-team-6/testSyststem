package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: ckd
 * Date: 2020/4/23
 * Time: 20:47
 */
public class PaperDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ProjectDao dao;
    private static UserDao userDao;
    private static PaperDao paperDao;

    @ClassRule
    public static DefaultJpaRule rule =new DefaultJpaRule();


    @Before
    public void setUp() {
        userDao = rule.getInjector().getInstance(UserDao.class);
        dao = rule.getInjector().getInstance(ProjectDao.class);
        paperDao = rule.getInjector().getInstance(PaperDao.class);
    }

    @Test
    public void testFindAll() {
        Project project = dao.findById(3);
        List<Paper> papers = paperDao.findAll(project);
        assertNotNull(papers);
        for(Paper p:papers){
            System.out.printf("Located Paper: %s\n", p);
        }
    }

    @Test
    public void testFindByUser() {
        Project project = dao.findById(3);
        User user = project.getFacilitator();
        List<Paper> papers = paperDao.findByUser(project,user);
        assertNotNull(papers);
        for(Paper p:papers){
            System.out.printf("Located Paper: %s\n", p);
        }
    }

    @Test
    public void testFindByPaperName(){
        Project project = dao.findById(3);
        List<Paper> papers = paperDao.findByPaperName(project,"试卷1");
        assertNotNull(papers);
        for(Paper p:papers){
            System.out.printf("Located Paper: %s\n", p);
        }
    }


    @Test
    public void testDelete(){
//        int lastId=paperDao.getMaxID();
//        Paper paper = paperDao.findById(lastId);
//        if (logger.isDebugEnabled()) {
//            logger.debug("Deleting question #{}. Details: {}", lastId, paper);
//        }
//        paperDao.delete(paper);
    }
}

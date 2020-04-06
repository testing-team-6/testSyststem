package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionLanguage;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class QuestionLanguageDaoTest {
    private QuestionLanguageDao dao;

    @ClassRule
    public static DefaultJpaRule jpaRule=new DefaultJpaRule();

    @Before
    public void setUp() {
        dao = jpaRule.getInjector().getInstance(QuestionLanguageDao.class);
    }

    @Test
    public void testCreate() {
        for (int i = 0; i < 5; i++) {
            QuestionLanguage language = new QuestionLanguage(Locale.getDefault().getDisplayName()+System.currentTimeMillis());
            dao.create(language);
            assertTrue(language.getId() > 0);
        }
    }

    @Test
    public void testUpdate() {
        QuestionLanguage language = dao.findById(dao.getMinID());
        language.setName("Status-"+System.currentTimeMillis());
        dao.update(language);
    }

    @Test
    public void testGetAll(){
        List<QuestionLanguage> languages=dao.findAll();
        assertNotNull(languages);
        System.out.println(languages);
    }

    @Test
    public void testGetById() {
        QuestionLanguage language = dao.findById(dao.getMinID());
        assertNotNull(language);
        System.out.println(language);
    }

    @Test
    public void testMinID(){
        Integer id=dao.getMinID();
        assertNotNull(id);
    }
    @Test
    public void testMaxID() {
        dao.create(new QuestionLanguage("zh-CN"+System.currentTimeMillis()));
        Integer id = dao.getMaxID();
        System.out.printf("Max id: %d\n",id);
        assertNotNull(id);
    }

    @Test
    public void testDelete(){
        int lastId=dao.getMaxID();
        dao.delete(dao.findById(lastId));
        QuestionLanguage l = dao.findById(lastId);
        assertNull(l);
    }
}

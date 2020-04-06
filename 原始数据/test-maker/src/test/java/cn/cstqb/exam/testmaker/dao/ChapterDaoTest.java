package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

import com.google.inject.Inject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:11
 */
public class ChapterDaoTest {
    @Inject SyllabusDao syllabusDao;
    @Inject ChapterDao chapterDao;

    @ClassRule
    public static AbstractJpaRule rule =new DefaultJpaRule(false);


    @Before
    public void setUp() {
        rule.getInjector().injectMembers(this);
    }

    @Test
    public void testCreate() {
        Integer id = syllabusDao.getMinID();
        Syllabus syllabus = syllabusDao.findById(id);
        Chapter chapter = new Chapter("4. Test Design Techniques", syllabus);
        chapter.setNumber("1.1");
        chapterDao.create(chapter);
        int dbID = chapter.getId();
        System.out.printf("Chapter saved with id: %d\n", dbID);
        assertTrue(dbID >= 0);
    }

    @Test
    public void testUpdate() {
        Syllabus syllabus = syllabusDao.findById(syllabusDao.getMinID());
        syllabus.setLevel("FL");
        syllabusDao.update(syllabus);
    }

    @Test
    public void testGetAll(){
        List<Syllabus> syllabuses= syllabusDao.findAll();
        assertNotNull(syllabuses);
        System.out.println(syllabuses);
    }

    @Test
    public void testGetById() {
        Syllabus syllabus = syllabusDao.findById(syllabusDao.getMaxID());
        assertNotNull(syllabus);
        System.out.println(syllabus);
    }

    @Test
    public void testMinID(){
        Serializable id= syllabusDao.getMinID();
        assertNotEquals(-1, id);
    }
    @Test
    public void testMaxID(){
        Serializable id= syllabusDao.getMaxID();
        assertTrue((Integer)id>1);
    }

    @Test
    public void testDelete() {
        Chapter chapter = chapterDao.last();
        chapterDao.delete(chapter);
    }

    @Test
    public void testFindChapter() {
    	Chapter chapter = chapterDao.findById(chapterDao.getMaxID());
    	Chapter toFindChapter = chapterDao.findChapter(chapter.getTitle());
    	assertEquals(toFindChapter, chapter);
    	System.out.printf("chapter id : %d  title : %s\n", toFindChapter.getId(), toFindChapter.getTitle());
    }


//    @Test
//    public void testFindChapters() {
//    	Syllabus syllabus = syllabusDao.findById(syllabusDao.getMinID());
//    	List<Chapter> chapters = chapterDao.findChapters(syllabus);
//    	assertNotNull(chapters);
//    	for (Chapter chapter : chapters){
//    		System.out.printf("chapter name : %s\n", chapter.getTitle());
//    	}
//    }
}

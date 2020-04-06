package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.dao.ChapterDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public class ChapterGenerationRule extends SyllabusGenerationRule {
    protected ChapterDao chapterDao;

    public ChapterGenerationRule() {
        super();
    }

    @Override
    protected void init() {
        super.init();
        chapterDao = injector.getInstance(ChapterDao.class);
    }

    public void populate() {
        super.populate();
        createChapters();
    }

    protected void createChapters() {
        int syllabusId=syllabusDao.getMinID();
        for (int i = 0; i < dataCount; i++) {
            Integer nextSyllabusId=syllabusId+i;
            Syllabus s = syllabusDao.findById(nextSyllabusId);
            Chapter ch = new Chapter(String.format("Chapter %d: %d", (i+1), System.currentTimeMillis()), s);
            chapterDao.create(ch);
        }
    }

}

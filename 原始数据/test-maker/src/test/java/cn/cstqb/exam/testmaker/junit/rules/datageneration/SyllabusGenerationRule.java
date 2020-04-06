package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.dao.SyllabusDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.AbstractDataGenerationRule;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public class SyllabusGenerationRule extends AbstractDataGenerationRule {
    protected SyllabusDao syllabusDao;

    public SyllabusGenerationRule() {
        super();
    }

    public void populate() {
        createSyllabus();
    }

    @Override
    protected void init() {
        syllabusDao = injector.getInstance(SyllabusDao.class);
    }

    protected void createSyllabus() {
        for (int i = 0; i < dataCount; i++) {
            Syllabus s = new Syllabus(String.format("ISTQB-%d (%d)", i,System.currentTimeMillis()), "Version " + i);
            syllabusDao.create(s);
        }
    }


}

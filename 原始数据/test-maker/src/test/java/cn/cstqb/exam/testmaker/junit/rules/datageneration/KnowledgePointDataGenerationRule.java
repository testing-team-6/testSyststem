package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.dao.KnowledgePointDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.AbstractDataGenerationRule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public class KnowledgePointDataGenerationRule extends ChapterGenerationRule {
    protected KnowledgePointDao knowledgePointDao;

    protected KnowledgePointDataGenerationRule() {
        super();
    }

    @Override
    protected void init() {
        super.init();
        knowledgePointDao = injector.getInstance(KnowledgePointDao.class);
    }

    public void populate() {
        super.populate();
        createKeyPoints();
    }

    protected void createKeyPoints() {
        Integer chapterId=chapterDao.getMinID();
        for (int i = 0; i < dataCount; i++) {
            Integer nextChapterId=chapterId+i;
            Chapter chapter = chapterDao.findById(chapterId);
            KnowledgePoint p = new KnowledgePoint(
                    String.format("%d.%d.%d", (i + 1), i > 0 ? i - 1 : i, System.currentTimeMillis()),
                    String.format("Knowledge Point %d", System.currentTimeMillis()),
                    String.format("K%d", (i+1)),
                    (short) i,
                    chapter
            );
            knowledgePointDao.create(p);
        }
    }

}

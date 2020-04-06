package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.ChapterDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 18:57
 */
public class ChapterDaoImpl extends GenericJpaDaoImpl<Chapter, Integer>
        implements ChapterDao {
    /**
     * find the chapter with the given title
     *
     * @param title
     * @return The chapter object matching the title or <i>null</i> if not found
     */
    @Override
    public Chapter findChapter(String title) {
        List<Chapter> results = findResultList("title", title);
        if (logger.isDebugEnabled()) {
            logger.debug("chapters matched for [{}]: {}", title, results != null ? results.size() : 0);
        }
        return results != null && !results.isEmpty() ? results.get(0) : null;
    }

    /**
     * Find chapters with the given syllabus
     *
     * @param syllabus
     * @return The chapter objects matching the syllabus or <i>null</i> if not
     * found
     */
    @Override
    public List<Chapter> findChapters(Syllabus syllabus) {
        List<Chapter> chapters = findResultList("syllabus", syllabus);
        if (logger.isDebugEnabled()) {
            logger.debug("Chapters detail for [{}] : {}", syllabus, chapters);
        }
        return chapters;
    }

    @Override
    public List<Chapter> findChapters(int syllabusId) {
        return provider.get()
                .createQuery("SELECT c FROM Chapter c WHERE c.syllabus.id = :syllabusId", Chapter.class)
                .setParameter("syllabusId", syllabusId)
                .getResultList();
    }
}

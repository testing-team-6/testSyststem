package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 18:57
 */
public interface ChapterDao extends GenericDao<Chapter, Integer> {
    /**
     * find the first chapter with the given title
     *
     * @param title
     * @return The first chapter object matching the title or <i>null</i> if not
     * found
     */
    Chapter findChapter(String title);

    /**
     * Find chapters with the given syllabus
     *
     * @param syllabus
     * @return The chapter objects matching the syllabus or <i>null</i> if not
     * found
     */
    List<Chapter> findChapters(Syllabus syllabus);

    List<Chapter> findChapters(int syllabusId);
}

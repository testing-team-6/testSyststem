package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Jian-Min Gao Date: 2015/1/8 Time: 21:49
 */
public interface IChapterService {
	/**
	 * Check if the provided chapter exists in DB or not
	 *
	 * @param chapter
	 * @return
	 */
	boolean exists(Chapter chapter);

	/**
	 * Creates or updates the provided chapter.
	 *
	 * @param chapter
	 *            The chapter to be created or updated.
	 */
	void saveOrUpdate(Chapter chapter);

	/**
	 * Deletes the given chapter.
	 *
	 * @param chapter
	 *            chapter to be deleted.
	 */
	void delete(Chapter chapter);

	/**
	 * Gets all chapter objects in the system.
	 *
	 * @return All chapters.
	 */
	List<Chapter> findAll();

	/**
	 * Get the chapter with the given id
	 *
	 * @param id
	 * @return
	 */
	Chapter findChapter(int id);

	/**
	 * Get the chapter with the given title
	 *
	 * @param title
	 * @return
	 */
	Chapter findChapter(String title);

	/**
	 * get the persisted chapter object with the given temp object
	 *
	 * @param chapter
	 * @return
	 */
	Chapter findChapter(Chapter chapter);

    List<Chapter> findChapters(Syllabus syllabus);

    /**
     * Find chapters by syllabus id
     * @param syllabusId
     * @return
     */
    List<Chapter> findChapters(int syllabusId);
}

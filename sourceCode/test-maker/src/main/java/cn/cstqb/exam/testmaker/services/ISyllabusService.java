package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 21:42
 */
public interface ISyllabusService {

    /**
     * Check if the provided syllabus exists in DB or not
     * @param syllabus
     * @return
     */
    boolean exists(Syllabus syllabus);

    boolean isSyllabusInUse(Syllabus syllabus);

	/**
	 * Creates or updates the provided syllabus
	 *
	 * @param syllabus
	 *            The syllabus to be created or updated.
	 */
	void saveOrUpdate(Syllabus syllabus) throws EntityAlreadyExistsException;

	/**
	 * Finds all knowledge points for the given chapter
	 *
	 * @param chapter
	 * @return
	 */
	List<KnowledgePoint> findKnowledgePoints(Chapter chapter);

	/**
	 * Finds all chapters for the given syllabus
	 *
	 * @param syllabus
	 * @return
	 */
	List<Chapter> findChapters(Syllabus syllabus);

	/**
	 * Finds all chapters and knowledge points for the syllabus
	 *
	 * @param syllabus
	 *            The syllabus to query
	 * @return A map of the knowledge points. The key is the chapter and the
	 *         value is all the related knowledge points for the chapter.
	 */
	Map<Chapter, List<KnowledgePoint>> findKnowledgePoints(Syllabus syllabus);

	/**
	 * Deletes the given syllabus.
	 *
	 * @param syllabus
	 *            Syllabus to be deleted.
	 */
	void delete(Syllabus syllabus);

	/**
	 * Gets all syllabus objects in the system
	 *
	 * @return All syllabuses.
	 */
	List<Syllabus> findAll();


	List<Syllabus> findActive();

	/**
	 * get the syllabus object with the given id
	 * @param id
	 * @return
	 */
	Syllabus findSyllabus(int id);

	/**
	 * get the persisted syllabus object with the given temp object
	 * @param syllabus
	 * @return
	 */
	Syllabus findSyllabus(Syllabus syllabus);
}

package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 21:50
 */
public interface IKnowledgePointService {
	/**
     * Creates or updates the provided knowledge point.
     * @param knowledgePoint The knowledge point to be created or updated.
     */
    void saveOrUpdate(KnowledgePoint knowledgePoint);

    /**
     * Deletes the given knowledge point.
     * @param knowledgePoint knowledge point to be deleted.
     */
    void delete(KnowledgePoint knowledgePoint);

    /**
     * Gets all knowledge point objects in the system.
     * @return All knowledge points.
     */
    List<KnowledgePoint> findAll();

    KnowledgePoint findKnowledgePoint(int id);

    KnowledgePoint findKnowledgePoint(String title);

    List<KnowledgePoint> findKnowledgePoints(Chapter chapter);

    /**
     *
     * @param chapterId
     * @return
     */
    List<KnowledgePoint> findKnowledgePoints(int chapterId);

    Long size();

    List<KnowledgePoint> findKnowledgePoints(int pageSize, int pageNumber);
    List<KnowledgePoint> findKnowledgePoints(Chapter chapter, int pageSize, int pageNumber);

    /**
     *
     * @param syllabus
     * @return
     */
    List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus);

    /**
     *
     * @param syllabus
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus, int pageSize, int pageNumber);

    List<Question> findUsingQuestions(KnowledgePoint knowledgePoint);
}

package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 19:14
 */
public interface KnowledgePointDao extends GenericDao<KnowledgePoint, Integer> {
    /**
     * Find knowledge points with the given chapter
     *
     * @param chapter
     * @return The knowledge point objects matching the chapter or <i>null</i> if not
     * found
     */
    List<KnowledgePoint> findKnowledgePoints(Chapter chapter);

    List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus, int pageSize, int pageNumber);

    /**
     * @param syllabus
     * @return
     */
    List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus);

    /**
     * @param chapterId
     * @return
     */
    List<KnowledgePoint> findKnowledgePoints(int chapterId);

    List<Question> findUsingQuestions(KnowledgePoint knowledgePoint);

    KnowledgePoint findKnowledgePoint(int id);

    KnowledgePoint findKnowledgePoint(String title);

}

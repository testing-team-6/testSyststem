package cn.cstqb.exam.testmaker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cn.cstqb.exam.testmaker.dao.KnowledgePointDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.Syllabus;

/**
 * Created with IntelliJ IDEA. User: Jian-Min Gao Date: 2014/12/26 Time: 19:14
 */
public class KnowledgePointDaoImpl extends
		GenericJpaDaoImpl<KnowledgePoint, Integer> implements KnowledgePointDao {
	/**
	 * Find knowledge points with the given chapter
	 *
	 * @param chapter
	 * @return The knowledge point objects matching the chapter or <i>null</i> if not
	 *         found
	 */
	@Override
	public List<KnowledgePoint> findKnowledgePoints(Chapter chapter) {
        List<KnowledgePoint> knowledgePoints = findResultList("chapter", chapter);

        if (logger.isDebugEnabled()) {
            logger.debug("Knowledge points details for [{}] : {}", chapter, knowledgePoints);
        }
        return knowledgePoints;
    }

    private TypedQuery<KnowledgePoint> buildQueryBySyllabus(Syllabus syllabus) {
        return provider.get().createQuery("SELECT k FROM KnowledgePoint k WHERE k.chapter.syllabus =:syllabus ORDER BY k.title ASC", KnowledgePoint.class)
                .setParameter("syllabus", syllabus);
    }

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus, int pageSize, int pageNumber) {
        logger.debug("KnowledgePointDaoImpl.findKnowledgePoints: Finding paginated knowledge points for syllabus id [{}]", syllabus.getId() );
        TypedQuery<KnowledgePoint> query = buildQueryBySyllabus(syllabus);
        return queryListPaginated(query, pageSize, pageNumber);
    }

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus) {
        logger.debug("KnowledgePointDaoImpl.findKnowledgePoints: Finding knowledge points for syllabus id [{}]", syllabus.getId() );
        return buildQueryBySyllabus(syllabus)
                .getResultList();
    }

    /**
     * @param chapterId
     * @return
     */
    @Override
    public List<KnowledgePoint> findKnowledgePoints(int chapterId) {
        EntityManager em=provider.get();
        return em.createQuery("SELECT k FROM KnowledgePoint k WHERE k.chapter.id =:chapterId ORDER BY k.title ASC", KnowledgePoint.class)
                .setParameter("chapterId", chapterId)
                .getResultList();
    }

    @Override
    public List<Question> findUsingQuestions(KnowledgePoint knowledgePoint) {
        logger.debug("KnowledgePointDaoImpl.findUsingQuestions: Finding questions using knowledge point #{}.", knowledgePoint.getId() );
        EntityManager em=provider.get();
        return em.createQuery("SELECT q FROM Question q WHERE q.knowledgePoint =:knowledgePoint", Question.class)
                .setParameter("knowledgePoint",knowledgePoint)
                .getResultList();
    }

    @Override
	public KnowledgePoint findKnowledgePoint(int id) {
		return findSingleResult("id", id);
	}

	@Override
	public KnowledgePoint findKnowledgePoint(String title) {
		return findSingleResult("title", title);
	}

}

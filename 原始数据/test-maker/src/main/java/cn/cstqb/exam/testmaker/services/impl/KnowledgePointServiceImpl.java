package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.KnowledgePointDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author wushuang
 *
 */

public class KnowledgePointServiceImpl implements IKnowledgePointService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private KnowledgePointDao knowledgePointDao;

	@Inject
	public KnowledgePointServiceImpl(KnowledgePointDao knowledgePointDao) {
		this.knowledgePointDao = knowledgePointDao;
	}

	/**
	 * Creates or updates the provided knowledge point.
	 *
	 * @param knowledgePoint
	 *            The knowledge point to be created or updated.
	 */
	@Override
	public void saveOrUpdate(KnowledgePoint knowledgePoint) {
		checkArgument(knowledgePoint != null,"Entity to be persisted cannot be null! %s", knowledgePoint.getClass().getSimpleName());
		checkArgument(knowledgePoint.validate()
						&& knowledgePoint.getChapter() != null,
				"The content of knowledge point can not be null or empty.");
		KnowledgePoint persisted = knowledgePointDao.findById(knowledgePoint
				.getId());
		if (persisted == null) {
			knowledgePointDao.create(knowledgePoint);
		} else {
			knowledgePointDao.update(knowledgePoint);
		}
	}

	/**
	 * Deletes the given knowledge point.
	 *
	 * @param knowledgePoint
	 *            knowledge point to be deleted.
	 */
	@Override
	public void delete(KnowledgePoint knowledgePoint) {
		checkArgument(knowledgePoint != null && knowledgePoint.validate() && knowledgePoint.isValidID());
		knowledgePointDao.delete(knowledgePoint);
	}

	/**
	 * Gets all knowledge point objects in the system.
	 *
	 * @return All knowledge points.
	 */
	@Override
	public List<KnowledgePoint> findAll() {
		return knowledgePointDao.findAll();
	}

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Chapter chapter) {
        if (chapter == null || !chapter.validate() ) {
            logger.warn("KnowledgePointServiceImpl.findKnowledgePoint: The provided chapter is null or invalid. All knowledge points will be returned. {}", chapter);
            return findAll();
        } else {
            return knowledgePointDao.findResultList("chapter", chapter);
        }
    }

    /**
     * @param chapterId
     * @return
     */
    @Override
    public List<KnowledgePoint> findKnowledgePoints(int chapterId) {
        Preconditions.checkArgument(chapterId>0,"Chapter id must be greater than zero.");
        return knowledgePointDao.findKnowledgePoints(chapterId);
    }

    @Override
	public KnowledgePoint findKnowledgePoint(int id) {
		return knowledgePointDao.findKnowledgePoint(id);
	}

	@Override
	public KnowledgePoint findKnowledgePoint(String title) {
		return knowledgePointDao.findKnowledgePoint(title);
	}

    @Override
    public List<KnowledgePoint> findKnowledgePoints(int pageSize, int pageNumber) {
        Preconditions.checkArgument(pageSize>1,"Page size must be greater than 1.");
        Preconditions.checkArgument(pageNumber>0,"Page number must be greater than 0.");
        return knowledgePointDao.findAll(pageSize, pageNumber);
    }

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Chapter chapter, int pageSize, int pageNumber) {
        if (chapter == null) {
            return findKnowledgePoints(pageSize, pageNumber);
        }
        Preconditions.checkState(chapter.validate(), "Invalid chapter content. %s", chapter);
        logger.debug("KnowledgePointServiceImpl.findKnowledgePoints: Loading knowledge points for [{}]", chapter );
        return knowledgePointDao.findResultList("chapter", chapter, pageSize, pageNumber);
    }

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus) {
        checkNotNull(syllabus);
        return knowledgePointDao.findKnowledgePoints(syllabus);
    }

    @Override
    public List<KnowledgePoint> findKnowledgePoints(Syllabus syllabus, int pageSize, int pageNumber) {
        if (syllabus == null) {
            return findKnowledgePoints(pageSize, pageNumber);
        }
        return knowledgePointDao.findKnowledgePoints(syllabus, pageSize, pageNumber);
    }

    @Override
    public Long size() {
        return knowledgePointDao.size();
    }

    @Override
    public List<Question> findUsingQuestions(KnowledgePoint knowledgePoint) {
        checkNotNull(knowledgePoint);
        checkArgument(knowledgePoint.validate());
        return knowledgePointDao.findUsingQuestions(knowledgePoint);
    }
}

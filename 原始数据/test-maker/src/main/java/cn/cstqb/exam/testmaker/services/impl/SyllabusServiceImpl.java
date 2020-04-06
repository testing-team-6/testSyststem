package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.SyllabusDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.exceptions.EntityAlreadyExistsException;
import cn.cstqb.exam.testmaker.services.IChapterService;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;
import cn.cstqb.exam.testmaker.services.ISyllabusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author wushuang
 *
 */
public class SyllabusServiceImpl implements ISyllabusService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private SyllabusDao dao;
    @Inject private IChapterService chapterService;
    @Inject private IKnowledgePointService knowledgePointService;

	@Inject
	public SyllabusServiceImpl(SyllabusDao dao) {
		this.dao = dao;
	}

    @Override
    public boolean exists(Syllabus syllabus) {
        if(syllabus==null) {
            logger.error("SyllabusServiceImpl.exists: Unable to check for existence. ID and level cannot be null/empty at the same time.");
            return false;
        }
        Syllabus persisted=dao.findSyllabus(syllabus);
        logger.debug("SyllabusServiceImpl.exists: Found syllabus from db: {}", persisted );
        return persisted!=null;
    }

    @Override
    public boolean isSyllabusInUse(Syllabus syllabus) {
        checkNotNull(syllabus);
        checkArgument(syllabus.validate());
        List<Project> projects = dao.findSyllabuses(syllabus);
        return projects!=null && !projects.isEmpty();
    }

    /**
	 * Creates or updates the provided syllabus
	 *
	 * @param syllabus
	 *            The syllabus to be created or updated.
	 */
	@Override
	public void saveOrUpdate(Syllabus syllabus) throws EntityAlreadyExistsException {
		checkArgument(syllabus != null,"Syllabus cannot be null.");
		checkArgument(syllabus.validate(),
				"The content of syllabus cannot be null or empty.");
		Syllabus persisted = dao.findById(syllabus.getId());
		if (persisted == null) {
            persisted = dao.findSyllabus(syllabus);
            if (persisted != null) {
                throw new EntityAlreadyExistsException(String.format("Syllabus already exists! Level: %s, Version: %s", syllabus.getLevel(), syllabus.getVersion()));
            }
            dao.create(syllabus);
        } else {
			dao.update(syllabus);
		}
	}

	/**
	 * Finds all knowledge points for the given chapter
	 *
	 * @param chapter
	 * @return
	 */
	@Override
	public List<KnowledgePoint> findKnowledgePoints(Chapter chapter) {
		checkArgument(chapter != null && chapter.getId() > 0,
				"Invalid chapter data. id : %s", chapter.getId());
		return knowledgePointService.findKnowledgePoints(chapter.getId());
	}

	/**
	 * Finds all chapters for the given syllabus
	 *
	 * @param syllabus
	 * @return
	 */
	@Override
	public List<Chapter> findChapters(Syllabus syllabus) {
        checkNotNull(syllabus);
        checkArgument(syllabus.validate() && syllabus.isValidID(), "Invalid syllabus data. id : %s", syllabus.getId());
        return chapterService.findChapters(syllabus);
    }

	/**
	 * Finds all chapters and knowledge points for the syllabus
	 *
	 * @param syllabus
	 *            The syllabus to query
	 * @return A map of the knowledge points. The key is the chapter and the
	 *         value is all the related knowledge points for the chapter.
	 */
	@Override
	public Map<Chapter, List<KnowledgePoint>> findKnowledgePoints(Syllabus syllabus) {
        checkNotNull(syllabus);
        checkArgument(syllabus.validate() && syllabus.isValidID(), "Invalid syllabus data: %s", syllabus);
        Map<Chapter, List<KnowledgePoint>> questionPrefix = new HashMap<>();
        List<Chapter> chapters = findChapters(syllabus);
        for (Chapter chapter : chapters) {
            List<KnowledgePoint> knowledgePoints = findKnowledgePoints(chapter);
            questionPrefix.put(chapter, knowledgePoints);
        }
        return questionPrefix;
    }

	/**
	 * Deletes the given syllabus.
	 *
	 * @param syllabus
	 *            Syllabus to be deleted.
	 */
	@Override
	public void delete(Syllabus syllabus) {
        checkNotNull(syllabus);
        checkArgument(syllabus.validate() && syllabus.isValidID(), "Invalid syllabus data: %s", syllabus);
		dao.delete(syllabus);
	}

	/**
	 * Gets all syllabus objects in the system
	 *
	 * @return All syllabuses.
	 */
	@Override
	public List<Syllabus> findAll() {
		return dao.findAll();
	}

    @Override
    public List<Syllabus> findActive() {
        return dao.findSyllabuses(false);
    }

    /**
	 * get the syllabus object with the given id
	 * @param id
	 * @return
	 */
	@Override
	public Syllabus findSyllabus(int id) {
		return dao.findById(id);
	}

	/**
	 * get the syllabus object with the given level
	 * @param syllabus
	 * @return
	 */
	@Override
	public Syllabus findSyllabus(Syllabus syllabus) {
		return dao.findSyllabus(syllabus);
	}



}

package cn.cstqb.exam.testmaker.modules.impl;

import java.util.List;

import static com.google.common.base.Preconditions.*;

import cn.cstqb.exam.testmaker.entities.Syllabus;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;
import cn.cstqb.exam.testmaker.modules.AbstractModuleWorker;
import cn.cstqb.exam.testmaker.modules.IChapterModuleWorker;
import cn.cstqb.exam.testmaker.services.IChapterService;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;

public class ChapterModuleWorkerImpl extends AbstractModuleWorker implements
		IChapterModuleWorker {

	private IChapterService chapterService;
	private IKnowledgePointService knowledgePointService;

	@Inject
	public ChapterModuleWorkerImpl(IChapterService chapterService, IKnowledgePointService knowledgePointService) {
		this.chapterService = chapterService;
		this.knowledgePointService = knowledgePointService;
	}

	/**
	 * Check if the currently logged-in user has privilege to access this worker
	 *
	 * @return
	 */
	@Override
	public boolean isIllegible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean chapterExists(Chapter chapter) {
		return chapterService.exists(chapter);
	}

	@Override
	public void createOrUpdate(Chapter chapter) throws Exception {
		if (chapter == null)
			throw new EntityPersistenceException(getText(
					"error.entity.invalid",
					Lists.newArrayList(Chapter.class.getName())));
		if (!chapter.validate())
			throw new EntityPersistenceException(getText(
					"error.entity.missRequiredFields",
					Lists.newArrayList(chapter)));
		chapterService.saveOrUpdate(chapter);
	}

	@Override
	public void delete(Chapter chapter) throws EntityPersistenceException {
		if (chapter == null || !chapter.validate() || chapter.getId() == null) {
			throw new EntityPersistenceException(getText(
					"error.entity.invalid",
					Lists.newArrayList(Chapter.class.getName())));
		}
		chapterService.delete(chapter);
	}

	@Override
	public Chapter findChapter(int id) {
		if (id < 1) {
			logger.error(
					"ChapterModuleWorkerImpl.findChapter: invalid ID provided: {}",
					id);
			return null;
		}
		return chapterService.findChapter(id);
	}

	@Override
	public Chapter findChapter(Chapter chapter) {
		if (chapter == null) {
			return null;
		}
		return chapterService.findChapter(chapter);
	}

	@Override
	public List<Chapter> findAll() {
		return chapterService.findAll();
	}
    /**
     * List all chapters for this syllabus
     *
     * @param syllabus
     * @return
     */
    @Override
    public List<Chapter> findChapters(Syllabus syllabus) {
        checkNotNull(syllabus);
        checkArgument(syllabus.validate() && syllabus.getId() != null && syllabus.getId() > 0);
        return chapterService.findChapters(syllabus);
    }

    @Override
	public void createOrUpdate(KnowledgePoint... knowledgePoints)
			throws EntityPersistenceException {
		if (knowledgePoints.length < 1) {
			return;
		}
		for (KnowledgePoint knowledgePoint : knowledgePoints) {
			knowledgePointService.saveOrUpdate(knowledgePoint);
		}
	}

}

package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.ChapterDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.services.IChapterService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author wushuang
 *
 */

public class ChapterServiceImpl implements IChapterService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ChapterDao chapterDao;

	@Inject
	public ChapterServiceImpl(ChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}

	/**
	 * check if the provided chapter exists in DB or not
	 */
	@Override
	public boolean exists(Chapter chapter) {
		if (chapter == null) {
			logger.error("ChapterServiceImpl.exists: Unable to check for existence. ID and title cannot be null/empty at the same time.");
			return false;
		}
		Chapter persisted = null;
		if (chapter.getId() != null && chapter.getId() > 0) {
			persisted = chapterDao.findById(chapter.getId());
		}else if (Strings.isNullOrEmpty(chapter.getTitle())) {
			persisted = chapterDao.findChapter(chapter.getTitle());
		}
		logger.debug("ChapterServiceImpl.exists: Found chapter from db: {}",
				persisted);
		return persisted != null;
	}

	/**
	 * Creates or updates the provided chapter.
	 *
	 * @param chapter
	 *            The chapter to be created or updated.
	 */
	@Override
	public void saveOrUpdate(Chapter chapter) {
		checkArgument(chapter != null, "Invalid chapter data. Chapter cannot be null.");
		checkArgument(!Strings.isNullOrEmpty(chapter.getTitle()) && chapter.getSyllabus() != null,
				"Invalid chapter data. Required fields: title, syllabus");
		Chapter persisted = chapterDao.findById(chapter.getId());
		logger.debug("ChapterService.saveOrUpdate: Chapter: {}", chapter);
		if (persisted == null) {
			chapterDao.create(chapter);
		} else {
			chapterDao.update(chapter);
		}
	}

	/**
	 * Deletes the given chapter.
	 *
	 * @param chapter
	 *            chapter to be deleted.
	 */
	@Override
	public void delete(Chapter chapter) {
		checkArgument(chapter != null && chapter.getId() > 0,
				"Invalid chapter data. id: %s", chapter.getId());
		chapterDao.delete(chapter);
	}

	/**
	 * Gets all chapter objects in the system.
	 *
	 * @return All chapters.
	 */
	@Override
	public List<Chapter> findAll() {
		return chapterDao.findAll();
	}

	/**
	 * Get the chapter with the given id
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Chapter findChapter(int id) {
		return chapterDao.findById(id);
	}

	/**
	 * Get the chapter with the given title
	 *
	 * @param title
	 * @return
	 */
	@Override
	public Chapter findChapter(String title) {
		return chapterDao.findChapter(title);
	}

	/**
	 * get the persisted chapter object with the given temp object
	 *
	 * @param chapter
	 * @return
	 */
	@Override
	public Chapter findChapter(Chapter chapter) {
		if (chapter != null && chapter.getId() != null && chapter.getId() > 0){
			return chapterDao.findById(chapter.getId());
		}
		else if (!Strings.isNullOrEmpty(chapter.getTitle())) {
			return chapterDao.findChapter(chapter.getTitle());
		}
		return null;
	}

    @Override
    public List<Chapter> findChapters(Syllabus syllabus) {
        return chapterDao.findChapters(syllabus);
    }

    @Override
    public List<Chapter> findChapters(int syllabusId) {
        Preconditions.checkArgument(syllabusId>0);
        return chapterDao.findChapters(syllabusId);
    }
}

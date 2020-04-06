package cn.cstqb.exam.testmaker.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.cstqb.exam.testmaker.dao.QuestionLanguageDao;
import cn.cstqb.exam.testmaker.entities.QuestionLanguage;
import cn.cstqb.exam.testmaker.guice.DaoModule;
import cn.cstqb.exam.testmaker.services.IQuestionLanguageService;

public class QuestionLanguageServiceImpl implements IQuestionLanguageService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private QuestionLanguageDao questionLanguageDao;
	
	@Inject
	public QuestionLanguageServiceImpl (QuestionLanguageDao questionLanguageDao) {
		this.questionLanguageDao = questionLanguageDao;
	}
	
	/**
	 * Create or update a question language
	 * @param questionLanguage
	 */
	@Override
	public void saveOrUpdate(QuestionLanguage questionLanguage) {
		Preconditions.checkArgument(questionLanguage != null, "Invalid question language.");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(questionLanguage.getName()), "The name of question language cannot be null or empty");
		QuestionLanguage persisted = questionLanguageDao.findById(questionLanguage.getId());
		if (persisted == null) {
			questionLanguageDao.create(questionLanguage);
		} else {
			questionLanguageDao.update(questionLanguage);
		}
	}

	/**
	 * Delete a question language
	 * @param questionLanguage
	 */
	@Override
	public void delete(QuestionLanguage questionLanguage) {
		Preconditions.checkArgument(questionLanguage != null, "Invalid question language.");
		questionLanguageDao.delete(questionLanguage);
	}

	/**
	 * Get all language objects
	 * @return
	 */
	@Override
	public List<QuestionLanguage> findAll() {
		return questionLanguageDao.findAll();
	}

	@Override
	public boolean exists(QuestionLanguage questionLanguage) {
//		if (questionLanguage != null) {
//			logger.debug("QuestionLanguageService.exists: question language object loaded is null.");
//			return false;
//		}
		QuestionLanguage persisted = null;
		if (questionLanguage.getId() != null && questionLanguage.getId() > 0){
			persisted = questionLanguageDao.findById(questionLanguage.getId());
		} else if (Strings.isNullOrEmpty(questionLanguage.getName())){
			persisted = questionLanguageDao.findSingleResult("name", questionLanguage.getName());
		}
		return persisted != null;
	}

	@Override
	public QuestionLanguage findQuestionLanguage(
			QuestionLanguage questionLanguage) {
		return questionLanguageDao.findById(questionLanguage.getId());
	}
	
}
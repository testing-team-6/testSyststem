package cn.cstqb.exam.testmaker.services;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.QuestionLanguage;

public interface IQuestionLanguageService {
	/**
	 * Create or update a question language
	 * @param questionLanguage
	 */
	void saveOrUpdate(QuestionLanguage questionLanguage);
	
	/**
	 * Delete a question language
	 * @param questionLanguage
	 */
	void delete(QuestionLanguage questionLanguage);
	
	/**
	 * Get all language objects
	 * @return
	 */
	List<QuestionLanguage> findAll();
	
	QuestionLanguage findQuestionLanguage(QuestionLanguage questionLanguage);
	
	boolean exists(QuestionLanguage questionLanguage);
}
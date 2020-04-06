package cn.cstqb.exam.testmaker.services;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;

public interface IQuestionChoiceService{
	/**
	 * Create or update a question choice
	 * @param questionChoice
	 */
	void createOrUpdate(QuestionChoice questionChoice);

	/**
	 * delete a question choice
	 * @param questionChoice
	 */
	void delete(QuestionChoice questionChoice);

	/**
	 * find the question to which the question choice belongs
	 * @param questionChoice
	 * @return
	 */
	Question findQuestion(QuestionChoice questionChoice);

	/**
	 * find the question choice with the provided id
	 * @param id
	 * @return
	 */
	QuestionChoice findQuestionChoice(int id);

	List<QuestionChoice> findQuestionChoices(QuestionChoice QuestionChoice);

	List<QuestionChoice> findQuestionChoices(Question question);

    /**
     * Find question choices by question id
     * @param questionId ID of the question to be searched
     * @return all choices for the question
     */
	List<QuestionChoice> findQuestionChoices(int questionId);

	QuestionChoice findLastQuestionChoice();

	/**
	 * judge if the question choice have the same label as other question choices which belong to the same question
	 * @param questionChoice
	 * @return true if other question choices belong to the same question have the same label
	 * 		   otherwise false
	 */
	boolean haveSameLabel(QuestionChoice questionChoice);

	/**
	 * judge if the number of correct answer match the question type
	 * single choice type should only have one correct answer
	 * @param questionChoice
	 * @return
	 */
	boolean correctAnswerMatchType(QuestionChoice questionChoice);

	boolean correctAnswerMatchType(Question question);

	boolean exists(QuestionChoice questionChoice);

}

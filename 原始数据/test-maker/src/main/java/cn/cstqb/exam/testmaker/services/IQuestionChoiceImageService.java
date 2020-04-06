package cn.cstqb.exam.testmaker.services;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;

public interface IQuestionChoiceImageService {
	
	void saveOrUpdate(QuestionChoiceImage questionChoiceImage);
	
	/**
	 * delete all the images with the provided choice
	 * @param
	 */
	void delete(QuestionChoiceImage questionChoiceImage);
	
	List<QuestionChoiceImage> listQuestionChoiceImagesByQuestionChoice(QuestionChoice questionChoice);
	
	boolean exists(QuestionChoiceImage questionChoiceImage);
	
	QuestionChoiceImage findQuestionChoiceImage(QuestionChoiceImage questionChoiceImage);

	QuestionChoiceImage findQuestionChoiceImage(int id);
}

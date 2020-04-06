package cn.cstqb.exam.testmaker.services;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionImage;

public interface IQuestionImageService {

	void saveOrUpdate(QuestionImage questionImage);
	
	void delete(QuestionImage questionImage);
	
	List<QuestionImage> listQuestionImagesByQuestion(Question question);
	
	boolean exists(QuestionImage questionImage);
	
	QuestionImage findQuestionImage(int id);
	
	QuestionImage findQuestionImage(QuestionImage questionImage);
	
	
}

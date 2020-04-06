package cn.cstqb.exam.testmaker.services;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.QuestionType;

public interface IQuestionTypeService {
	
	void saveOrUpdate(QuestionType questionType);
	
	void delete(QuestionType questionType);
	
	List<QuestionType> findAll();
	
	QuestionType findQuestionType(QuestionType questionType);
	
	boolean exists(QuestionType questionType);
}

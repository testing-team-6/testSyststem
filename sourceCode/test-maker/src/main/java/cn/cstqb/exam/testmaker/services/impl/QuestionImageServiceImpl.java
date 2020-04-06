package cn.cstqb.exam.testmaker.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import cn.cstqb.exam.testmaker.dao.QuestionImageDao;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionImage;
import cn.cstqb.exam.testmaker.services.IQuestionImageService;

public class QuestionImageServiceImpl implements IQuestionImageService{
	private Logger Logger = LoggerFactory.getLogger(getClass());
	private QuestionImageDao dao;
	
	@Inject
	public QuestionImageServiceImpl(QuestionImageDao dao){
		this.dao = dao;
	}
	
	@Override
	public void saveOrUpdate(QuestionImage questionImage) {
		Preconditions.checkArgument(questionImage != null);
		QuestionImage persisted = dao.findById(questionImage.getId());
		if (persisted == null) {
			dao.create(questionImage);
		} else {
			dao.update(questionImage);
		}
	}

	@Override
	public void delete(QuestionImage questionImage) {
		Preconditions.checkArgument(questionImage != null && questionImage.getId() > 0);
		dao.delete(questionImage);
	}

	@Override
	public List<QuestionImage> listQuestionImagesByQuestion(Question question) {
		return dao.findResultList("question", question);
	}

	@Override
	public boolean exists(QuestionImage questionImage) {
		QuestionImage persisted = null;
		if (questionImage.getId() != null && questionImage.getId() > 0) {
			persisted = dao.findById(questionImage.getId());
		}
		return persisted != null;
	}

	@Override
	public QuestionImage findQuestionImage(int id) {
		return dao.findById(id);
	}
	
	@Override
	public QuestionImage findQuestionImage(QuestionImage questionImage) {
		return dao.findById(questionImage.getId());
	}

}

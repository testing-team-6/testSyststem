package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.QuestionChoiceImageDao;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceImageService;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuestionChoiceImageServiceImpl implements IQuestionChoiceImageService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private QuestionChoiceImageDao dao;

	@Inject
	public QuestionChoiceImageServiceImpl(QuestionChoiceImageDao dao){
		this.dao = dao;
	}

	@Override
	public void saveOrUpdate(QuestionChoiceImage questionChoiceImage) {
		Preconditions.checkArgument(questionChoiceImage != null);
		QuestionChoiceImage persisted = dao.findById(questionChoiceImage.getId());
		if (persisted == null) {
			dao.create(questionChoiceImage);
		} else {
			dao.update(questionChoiceImage);
		}
	}

	/**
	 * delete all the images with the provided choice
	 */
	@Override
	public void delete(QuestionChoiceImage questionChoiceImage) {
		Preconditions.checkArgument(questionChoiceImage != null && questionChoiceImage.getId() > 0);
		dao.delete(questionChoiceImage);
	}

	@Override
	public List<QuestionChoiceImage> listQuestionChoiceImagesByQuestionChoice(QuestionChoice questionChoice) {
		return dao.findResultList("choice", questionChoice);
	}

	@Override
	public boolean exists(QuestionChoiceImage questionChoiceImage) {
		QuestionChoiceImage persisted = null;
		if (questionChoiceImage.getId() != null && questionChoiceImage.getId() > 0) {
			persisted = dao.findById(questionChoiceImage.getId());
		}
		return persisted != null;
	}

	@Override
	public QuestionChoiceImage findQuestionChoiceImage(
			QuestionChoiceImage questionChoiceImage) {
		return dao.findById(questionChoiceImage.getId());
	}

	@Override
	public QuestionChoiceImage findQuestionChoiceImage(int id) {
		return dao.findById(id);
	}

}

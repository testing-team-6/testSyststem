package cn.cstqb.exam.testmaker.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import cn.cstqb.exam.testmaker.dao.QuestionTypeDao;
import cn.cstqb.exam.testmaker.entities.QuestionType;
import cn.cstqb.exam.testmaker.services.IQuestionTypeService;

public class QuestionTypeServiceImpl implements IQuestionTypeService {
	Logger logger = LoggerFactory.getLogger(getClass());
	private QuestionTypeDao dao;
	
	@Inject
	public QuestionTypeServiceImpl(QuestionTypeDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void saveOrUpdate(QuestionType questionType) {
		Preconditions.checkArgument(questionType != null, "QuestionTyperService.saveOrUpdate: empty entity loaded!");
		QuestionType persisted = dao.findById(questionType.getId());
		if (persisted == null) {
			dao.create(questionType);
		} else {
			dao.update(questionType);
		}
	}

	@Override
	public void delete(QuestionType questionType) {
		Preconditions.checkArgument(questionType != null, "QuestionTypeService.delete: empty entity loaded!");
		dao.delete(questionType);
	}

	@Override
	public List<QuestionType> findAll() {
		return dao.findAll();
	}

	@Override
	public boolean exists(QuestionType questionType) {
		QuestionType persisted = null;
		if (questionType.getId() != null && questionType.getId() > 0) {
			persisted = dao.findById(questionType.getId());
		}else if (!Strings.isNullOrEmpty(questionType.getName())) {
			persisted = dao.findSingleResult("name", questionType.getName());
		}
		return persisted != null;
	}

	@Override
	public QuestionType findQuestionType(QuestionType questionType) {
		if (questionType.getId() != null && questionType.getId() > 0) {
			return dao.findById(questionType.getId());
		} else if (!Strings.isNullOrEmpty(questionType.getName())) {
			return dao.findSingleResult("name", questionType.getName());
		}
		return null;
	}

}

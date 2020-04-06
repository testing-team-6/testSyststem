package cn.cstqb.exam.testmaker.actions.questionType;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionType;

import com.google.common.collect.Lists;

public class UpdateQuestionTypeAction extends BaseQuestionTypeAction {

	@Override
	public void validateInput() {
		super.validateInput();
		if (getQuestionType().getId() == null && getQuestionType().getId() < 0) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(QuestionType.class.getSimpleName())));
		}
	}

	@Override
	protected String executeImpl() throws Exception {
		if (!questionTypeService.exists(getQuestionType())) {
			addActionError(getText("error.entity.not.exist", Lists.newArrayList(getQuestionType())));
			return Constants.RESULT_NOT_FOUND;
		}
		logger.debug("UpdateQuestionTypeAction.executeImpl: loading persisted version for this question type: {}", getQuestionType());
		QuestionType persisted = questionTypeService.findQuestionType(getQuestionType());
		if (persisted.equals(getQuestionType())) {
			addActionError(getText("error.entity.notModified", Lists.newArrayList(persisted)));
			return Constants.RESULT_NOT_MODIFIED;
		}
		questionTypeService.saveOrUpdate(getQuestionType());
		return null;
	}

}

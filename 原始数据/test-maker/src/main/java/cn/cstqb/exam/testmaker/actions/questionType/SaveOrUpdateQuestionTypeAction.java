package cn.cstqb.exam.testmaker.actions.questionType;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionType;

import com.google.common.collect.Lists;

public class SaveOrUpdateQuestionTypeAction extends BaseQuestionTypeAction {

	@Override
	protected String executeImpl() throws Exception {

		if (getQuestionType().getId() == null) {
			if (questionTypeService.exists(getQuestionType())) {
				addActionError(getText("error.entity.alreadyExists", Lists.newArrayList(questionTypeService.findQuestionType(getQuestionType()).getId())));
				return Constants.RESULT_ALREADY_EXISTS;
			}
		} else if (questionType.getId() != null && questionType.getId() > 0) {
			if (!questionTypeService.exists(questionType)) {
				addActionError(getText("error.entity.not.exist", Lists.newArrayList(getQuestionType())));
				return Constants.RESULT_NOT_FOUND;
			}
			logger.debug("UpdateQuestionTypeAction.executeImpl: loading persisted version for this question type: {}", getQuestionType());
			QuestionType persisted = questionTypeService.findQuestionType(questionType);
			if (persisted.equals(questionType)) {
				addActionError(getText("error.entity.notModified", Lists.newArrayList(persisted)));
				return Constants.RESULT_NOT_MODIFIED;
			}
		}
		questionTypeService.saveOrUpdate(questionType);
		return null;
	}

}

package cn.cstqb.exam.testmaker.actions.questionType;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.QuestionType;
import cn.cstqb.exam.testmaker.services.IQuestionTypeService;

public abstract class BaseQuestionTypeAction extends BaseAction {

	protected QuestionType questionType;
	protected IQuestionTypeService questionTypeService;

	public BaseQuestionTypeAction() {
		super();
		questionTypeService = injector.getInstance(IQuestionTypeService.class);
	}

	@Override
	public void validateInput() {
		if (questionType == null) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(QuestionType.class.getSimpleName())));
			return;
		}
		if (Strings.isNullOrEmpty(questionType.getName())) {
			addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("message.questionType.requiredFields"))));
			return;
		}
		if (questionType.getId() != null) {
			if (questionType.getId() < 0) {
				addActionError(getText("error.entity.invalid", Lists.newArrayList(QuestionType.class.getSimpleName())));
				return;
			}
		}
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

}

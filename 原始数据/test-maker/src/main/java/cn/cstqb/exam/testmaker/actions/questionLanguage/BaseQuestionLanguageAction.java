package cn.cstqb.exam.testmaker.actions.questionLanguage;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.QuestionLanguage;
import cn.cstqb.exam.testmaker.services.IQuestionLanguageService;

public abstract class BaseQuestionLanguageAction extends BaseAction {

	protected QuestionLanguage questionLanguage;
	protected IQuestionLanguageService questionLanguageService;

	public BaseQuestionLanguageAction() {
		super();
		questionLanguageService = injector.getInstance(IQuestionLanguageService.class);
	}

	@Override
	public void validateInput() {
		if (questionLanguage == null) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.questionLanguage"))));
			return;
		}
		if (Strings.isNullOrEmpty(questionLanguage.getName())){
			addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("message.questionLanguage.requiredFields"))));
			return;
		}
		if (questionLanguage.getId() != null) {
			if (questionLanguage.getId() < 0) {
				addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.questionLanguage"))));
				return;
			}
		}
	}

	public QuestionLanguage getQuestionLanguage() {
		return questionLanguage;
	}

	public void setQuestionLanguage(QuestionLanguage questionLanguage) {
		this.questionLanguage = questionLanguage;
	}


}

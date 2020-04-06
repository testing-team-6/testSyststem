package cn.cstqb.exam.testmaker.actions.questionChoice;

import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;

public abstract class BaseQuestionChoiceAction extends BaseAction{
	protected IQuestionChoiceService questionChoiceService;
	protected QuestionChoice choice;

    public QuestionChoice getChoice() {
        return choice;
    }

    public void setChoice(QuestionChoice choice) {
        this.choice = choice;
    }

    public BaseQuestionChoiceAction() {
		super();
		this.questionChoiceService = injector.getInstance(IQuestionChoiceService.class);
	}

	@Override
	public void validateInput() {
		if (choice == null) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question.choice"))));
			return;
		}

		if (!choice.validate()) {
			addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("message.questionChoice.requiredFields"))));
			return;
		}
	}
}

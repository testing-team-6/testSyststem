package cn.cstqb.exam.testmaker.actions.questionChoiceImage;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceImageService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import com.google.inject.Inject;

abstract class BaseQuestionChoiceImageAction extends BaseAction {

	@Inject protected IQuestionChoiceImageService choiceImageService;
	@Inject protected IQuestionChoiceService choiceService;

	public BaseQuestionChoiceImageAction() {
		super();
        injector.injectMembers(this);
	}


}

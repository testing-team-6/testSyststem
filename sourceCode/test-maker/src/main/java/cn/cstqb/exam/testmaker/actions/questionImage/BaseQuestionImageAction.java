package cn.cstqb.exam.testmaker.actions.questionImage;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.services.IQuestionImageService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import com.google.inject.Inject;

abstract class BaseQuestionImageAction extends BaseAction {
    @Inject protected IQuestionService questionService;
	@Inject protected IQuestionImageService questionImageService;

	public BaseQuestionImageAction() {
		super();
        injector.injectMembers(this);
	}

}

package cn.cstqb.exam.testmaker.actions.questionType;

import java.util.List;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionType;
import com.google.common.collect.Lists;

public class ListQuestionTypeAction extends BaseQuestionTypeAction {

	private List<QuestionType> aaData;

	@Override
	public void validateInput() {}

	@Override
	protected String executeImpl() throws Exception {
		aaData = questionTypeService.findAll();
		if (aaData == null || aaData.isEmpty()) {
			logger.warn("ListQuestionTypeAction.execute: NO RESULT FOUND!");
            addActionError(getText("error.no.content", Lists.newArrayList(getText("label.entity.questionType"))));
			return Constants.RESULT_NO_CONTENT;
		}
		logger.info("ListQuestionTypeAction.execute: question types found: {}", aaData.size()+"");
		return null;
	}

	public List<QuestionType> getAaData() {
		return aaData;
	}

	public void setAaData(List<QuestionType> aaData) {
		this.aaData = aaData;
	}

}

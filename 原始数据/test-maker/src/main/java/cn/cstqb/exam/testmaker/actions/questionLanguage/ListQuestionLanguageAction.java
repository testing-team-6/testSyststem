package cn.cstqb.exam.testmaker.actions.questionLanguage;

import java.util.List;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionLanguage;
import com.google.common.collect.Lists;

public class ListQuestionLanguageAction extends BaseQuestionLanguageAction {

	private List<QuestionLanguage> aaData;

	@Override
	public void validateInput() {};

	@Override
	protected String executeImpl() throws Exception {
		aaData = questionLanguageService.findAll();
		if (aaData == null || aaData.isEmpty()) {
			logger.warn("ListQuestionLanguageAction.execute: NO RESULT FOUND!");
            addActionError(getText("error.no.content", Lists.newArrayList(getText("label.entity.questionLanguage"))));
            return Constants.RESULT_NO_CONTENT;
		}
		logger.info("ListQuestionLanguageAction.execute: question languages found: {}", aaData.size()+"");
		return null;
	}

	public List<QuestionLanguage> getAaData() {
		return aaData;
	}

	public void setAaData(List<QuestionLanguage> aaData) {
		this.aaData = aaData;
	}

}

package cn.cstqb.exam.testmaker.actions.questionLanguage;

import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionLanguage;

public class SaveOrUpdateQuestionLanguageAction extends BaseQuestionLanguageAction {

	@Override
	protected String executeImpl() throws Exception {
		if (questionLanguage.getId() == null){
			//create
			if (questionLanguageService.exists(questionLanguage)){
				addActionError(getText("error.entity.alreadyExists", Lists.newArrayList(getText("label.entity.questionLanguage"))));
				return Constants.RESULT_ALREADY_EXISTS;
			}
		} else if (questionLanguage.getId() != null && questionLanguage.getId() > 0) {
			//update
			if (!questionLanguageService.exists(questionLanguage)) {
				addActionError(getText("error.entity.not.exist", Lists.newArrayList(questionLanguage)));
				return Constants.RESULT_NOT_FOUND;
			}
			logger.debug("UpdateQuestionLanguage.executeImpl: loading persisted version for this question language: {}", questionLanguage);
			QuestionLanguage persisted = questionLanguageService.findQuestionLanguage(questionLanguage);
			if (persisted.equals(questionLanguage)) {
				addActionError(getText("error.entity.notModified", Lists.newArrayList(questionLanguage)));
				return Constants.RESULT_NOT_MODIFIED;
			}
		}
		questionLanguageService.saveOrUpdate(questionLanguage);
		return null;
	}

}

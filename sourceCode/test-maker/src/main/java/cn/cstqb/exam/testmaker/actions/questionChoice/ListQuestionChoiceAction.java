package cn.cstqb.exam.testmaker.actions.questionChoice;

import java.util.List;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import org.apache.struts2.json.annotations.JSON;

public class ListQuestionChoiceAction extends BaseQuestionChoiceAction {

	private int questionId;
	private List<QuestionChoice> choices;

	@Override
	public void validateInput() {};

	@Override
	protected String executeImpl() throws Exception {
        logger.debug("ListQuestionChoiceAction.executeImpl: Finding all choices for question #0", questionId );
        choices = questionChoiceService.findQuestionChoices(questionId);
		logger.info("ListQuestionChoiceAction.executeImpl: Found aaData {}", choices.size()+"");
		if (choices == null) {
			return Constants.RESULT_NOT_FOUND;
		}
		return null;
	}

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    @JSON(serialize = false)
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}

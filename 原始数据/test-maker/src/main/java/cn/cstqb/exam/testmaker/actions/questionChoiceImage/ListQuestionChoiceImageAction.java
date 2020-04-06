package cn.cstqb.exam.testmaker.actions.questionChoiceImage;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import com.google.common.collect.Lists;

import java.util.List;

public class ListQuestionChoiceImageAction extends BaseQuestionChoiceImageAction {

	private int choiceId;
	private List<QuestionChoiceImage> images;
    private QuestionChoice questionChoice;


    @Override
	public void validateInput() {
        if (choiceId<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(choiceId)));
            return;
        }
        questionChoice = choiceService.findQuestionChoice(choiceId);
        if (questionChoice == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(choiceId, getText("label.entity.question.choice"))));
            return;
        }
    }

	@Override
	protected String executeImpl() throws Exception {
        images = choiceImageService.listQuestionChoiceImagesByQuestionChoice(questionChoice);
		if (images == null || images.isEmpty()) {
			logger.warn("ListQuestionChoiceImageAction.execute: NO RESULT FOUND!");
            addActionError(getText("error.no.content", Lists.newArrayList(getText("label.entity.question.choice.image"))));
			return Constants.RESULT_NO_CONTENT;
		}
		logger.info("ListQuestionChoiceImageAction.execute: question choice images found: #0", images.size()+"");
		return null;
	}


	public void setQuestionChoice(QuestionChoice questionChoice) {
		this.questionChoice = questionChoice;
	}

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public List<QuestionChoiceImage> getImages() {
        return images;
    }

    public void setImages(List<QuestionChoiceImage> images) {
        this.images = images;
    }
}

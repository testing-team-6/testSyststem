package cn.cstqb.exam.testmaker.actions.questionImage;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionImage;
import com.google.common.collect.Lists;

import java.util.List;

public class ListQuestionImageAction extends BaseQuestionImageAction {

    private int questionId;
	private Question question;
	private List<QuestionImage> images;

	@Override
	public void validateInput() {
        if (questionId<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(questionId)));
            return;
        }
        question = questionService.findQuestion(questionId);
        if (question == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(questionId, getText("label.entity.question"))));
            return;
        }
    }

	@Override
	protected String executeImpl() throws Exception {
        logger.debug("ListQuestionImageAction.executeImpl: Listing images for question #0",questionId );
        images = questionImageService.listQuestionImagesByQuestion(question);
		logger.info("ListQuestionImageAction.execute: question images found: {}", images.size() + "");
		return null;
	}

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<QuestionImage> getImages() {
        return images;
    }
}

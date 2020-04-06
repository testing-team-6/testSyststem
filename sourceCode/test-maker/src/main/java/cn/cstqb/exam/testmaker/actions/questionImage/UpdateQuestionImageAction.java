package cn.cstqb.exam.testmaker.actions.questionImage;

import cn.cstqb.exam.testmaker.entities.QuestionImage;
import com.google.common.collect.Lists;

public class UpdateQuestionImageAction extends BaseQuestionImageAction {

    private QuestionImage image;

	@Override
	public void validateInput() {
        if (image == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question.image"))));
            return;
        }

        if (!image.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(image.getId())));
            return;
        }

        if (!image.validate() ) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.image.path"))));
            return;
        }
    }

	@Override
	protected String executeImpl() throws Exception {
        logger.debug("UpdateQuestionImageAction.executeImpl: Updating caption/path for image #0", image.getId());
        QuestionImage persisted = questionImageService.findQuestionImage(image.getId());
        persisted.setCaption(image.getCaption());
        questionImageService.saveOrUpdate(persisted);
        logger.debug("UpdateQuestionImageAction.executeImpl: Updating caption/path for image #0 successful!", persisted.getId());
        return null;
	}

    public void setImage(QuestionImage image) {
        this.image = image;
    }
}

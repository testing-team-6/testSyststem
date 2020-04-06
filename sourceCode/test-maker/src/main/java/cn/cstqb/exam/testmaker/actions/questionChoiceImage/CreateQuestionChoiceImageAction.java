package cn.cstqb.exam.testmaker.actions.questionChoiceImage;

import cn.cstqb.exam.testmaker.actions.AbstractFileUploadAction;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceImageService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class CreateQuestionChoiceImageAction extends AbstractFileUploadAction {
	@Inject private IQuestionChoiceService choiceService;
    @Inject private IQuestionChoiceImageService questionChoiceImageService;

    private int choiceId;
    private QuestionChoice choice;

    public CreateQuestionChoiceImageAction() {
    	super();
        injector.injectMembers(this);
	}

    @Override
    protected Project getContainingProject() {
        return choice.getQuestion().getProject();
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        super.validateInput();
        if (choiceId<1) {
            addActionError(getText("error.entity.id.invalid"));
            return;
        }
        choice = choiceService.findQuestionChoice(choiceId);
        if (choice == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.question.choice"))));
            return;
        }
    }

    @Override
    protected void postUpload() {
        logger.debug("CreateQuestionChoiceImageAction.postUpload: saving choiceImage info to DB. #0", choiceId );
        QuestionChoiceImage choiceImage = new QuestionChoiceImage();
        choiceImage.setChoice(choice);
        choiceImage.setCaption(caption);
        choiceImage.setPath(getServerPath());
        questionChoiceImageService.saveOrUpdate(choiceImage);
        logger.debug("CreateQuestionChoiceImageAction.postUpload: Image info saved to DB successfully. Image ID: #0", choiceImage.getId());
    }

    protected void initTargetFilePath() {
        targetFilePath = String.format("%s\\%s\\%s",
                choice.getQuestion().getId(),
                choice.getId(),
                getTargetFileName()
        );
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }
}

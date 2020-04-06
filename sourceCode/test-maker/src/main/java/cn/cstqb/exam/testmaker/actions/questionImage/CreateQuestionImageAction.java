package cn.cstqb.exam.testmaker.actions.questionImage;

import cn.cstqb.exam.testmaker.actions.AbstractFileUploadAction;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionImage;
import cn.cstqb.exam.testmaker.services.IQuestionImageService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class CreateQuestionImageAction extends AbstractFileUploadAction {

	@Inject private IQuestionService questionService;
    @Inject private IQuestionImageService questionImageService;
	private int questionId;
	private Question question;

    public CreateQuestionImageAction() {
    	super();
        injector.injectMembers(this);
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
        if (questionId < 1) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(Question.class.getSimpleName())));
            return;
        }
        question = questionService.findQuestion(questionId);
        if (question == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.question"))));
            return;
        }
    }

    @Override
    protected void initTargetFilePath() {
        targetFilePath = String.format("%s/%s",
                question.getId(),
                getTargetFileName()
        );
    }

    @Override
    protected Project getContainingProject() {
        return question.getProject();
    }

    @Override
    protected void postUpload() {
        logger.debug("CreateQuestionImageAction.postUpload: saving image info for {}", questionId );
        QuestionImage image = new QuestionImage();
        image.setCaption(caption);
        image.setPath(getServerPath());
        image.setQuestion(question);
        questionImageService.saveOrUpdate(image);
        logger.debug("CreateQuestionImageAction.postUpload: Image saved successfully:\n{}", image);
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}

package cn.cstqb.exam.testmaker.actions.questionChoiceImage;

import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/20
 * Time: 19:43
 */
public class UpdateChoiceImageAction extends BaseQuestionChoiceImageAction {
    private QuestionChoiceImage choiceImage;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (choiceImage == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question.choice.image"))));
            return;
        }

        if (!choiceImage.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(choiceImage.getId())));
            return;
        }

        if (!choiceImage.validate() ) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.image.path"))));
            return;
        }
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("UpdateChoiceImageAction.executeImpl: Updating caption/path for image #0", choiceImage.getId());
        QuestionChoiceImage image = choiceImageService.findQuestionChoiceImage(choiceImage.getId());
        image.setCaption(choiceImage.getCaption());
        choiceImageService.saveOrUpdate(image);
        logger.debug("UpdateChoiceImageAction.executeImpl: Updating caption/path for image #0 successful!", choiceImage.getId());
        return null;
    }

    public void setChoiceImage(QuestionChoiceImage choiceImage) {
        this.choiceImage = choiceImage;
    }
}

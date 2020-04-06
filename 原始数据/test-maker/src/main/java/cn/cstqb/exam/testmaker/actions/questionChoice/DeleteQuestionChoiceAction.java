package cn.cstqb.exam.testmaker.actions.questionChoice;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import com.google.common.collect.Lists;

public class DeleteQuestionChoiceAction extends BaseQuestionChoiceAction {
    private int id;

    @Override
    public void validateInput() {
        if (id < 1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(id)));
            return;
        }
    }

    @Override
	protected String executeImpl() throws Exception {
        QuestionChoice target = questionChoiceService.findQuestionChoice(id);
        if (target == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(id, getText("label.entity.question.choice"))));
            return Constants.RESULT_NOT_FOUND;
        }

        logger.debug("DeleteQuestionChoiceAction.executeImpl: loading persisted version for this question choice: #0", target);
        questionChoiceService.delete(target);
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

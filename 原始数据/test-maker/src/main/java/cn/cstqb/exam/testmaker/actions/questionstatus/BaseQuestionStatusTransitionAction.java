package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/28
 * Time: 12:50
 */
public abstract class BaseQuestionStatusTransitionAction extends BaseQuestionStatusAction {
    protected QuestionStatus status;
    protected List<QuestionStatus> statuses;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (!validateStatus(status)) {
            return;
        }
        if (!status.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(status.getId())));
            return;
        }

    }
    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public List<QuestionStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<QuestionStatus> statuses) {
        this.statuses = statuses;
    }
}

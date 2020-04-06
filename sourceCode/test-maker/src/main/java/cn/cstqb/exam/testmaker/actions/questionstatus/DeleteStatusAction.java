package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/27
 * Time: 21:47
 */
public class DeleteStatusAction extends BaseQuestionStatusAction {
    private QuestionStatus status;
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
            logger.error("DeleteStatusAction.validateInput: Invalid ID for status: #0", status );
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(status)));
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
        QuestionStatus persisted = statusService.findStatus(status.getId());
        if (persisted==null) {
            logger.error("SaveQuestionStatusAction.executeImpl: Status not found. #0", persisted.toString() );
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(status)));
            return Constants.RESULT_NOT_FOUND;
        }

        logger.debug("SaveQuestionStatusAction.executeImpl: #0", status.toString());
        statusService.delete(status);
        return null;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }
}

package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import com.google.common.collect.Lists;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/28
 * Time: 12:37
 */
public class SaveTransitionalStatusAction extends BaseQuestionStatusTransitionAction {

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
        if (statuses==null||statuses.isEmpty()) {
            logger.error("SaveTransitionalStatusAction.validateInput: No transitional states provided. The list is null/empty." );
            addActionError(getText("error.question.status.transition.missing", Lists.newArrayList(status.getName())));
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
        logger.debug("SaveTransitionalStatusAction.executeImpl: Saving transitional statuses for #0", status.toString() );
        statusService.saveTransitionalStates(status, new HashSet<>(statuses));
        return null;
    }
}

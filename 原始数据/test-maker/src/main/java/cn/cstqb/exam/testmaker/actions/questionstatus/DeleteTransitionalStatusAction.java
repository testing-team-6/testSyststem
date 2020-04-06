package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/28
 * Time: 13:45
 */
public class DeleteTransitionalStatusAction extends BaseQuestionStatusTransitionAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("DeleteTransitionalStatusAction.executeImpl: deleting transitions for #0", status.getName());
        statusService.clearTransitions(status);
        return null;
    }
}

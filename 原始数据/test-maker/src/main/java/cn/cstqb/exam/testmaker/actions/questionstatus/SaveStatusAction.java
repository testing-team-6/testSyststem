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
public class SaveStatusAction extends BaseQuestionStatusAction {
    private QuestionStatus status;
    private QuestionStatus persisted;
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
        if (status.isStart() && status.isFinish()) {
            logger.error("SaveQuestionStatusAction.executeImpl: A status cannot be start state and finish state at the same time.", status.toString() );
            addActionError(getText("error.question.status.bothStartAndFinish", Lists.newArrayList(status.getName())));
            return;
        }

        /*
         * If a status with the same name can found in DB and the incoming status does not have an ID,
         * this is a save attempt. In this case, stop it
         */
        persisted = statusService.findStatus(status.getName());
        if (!status.isValidID() && persisted!=null) {
            logger.error("SaveQuestionStatusAction.validateInput: A status with the name already exists. #0", persisted.toString() );
            addActionError(getText("error.entity.alreadyExists", Lists.newArrayList(String.format("%d: \"%s\"", persisted.getId(), persisted.getName()))));
            return;
        }

        /*
         * Now we can say it's an update attempt
         */
        logger.debug("SaveStatusAction.validateInput: Attempting to update status: #0", status );
        if (status.isValidID()) {
            persisted = statusService.findStatus(status.getId());

            //check if the start status is the incoming one
            if (status.isStart()) {
                QuestionStatus startStatus=statusService.findStartStatus();
                if (!status.getId().equals(startStatus.getId())) {
                    logger.error("SaveStatusAction.executeImpl: Unable to add status as start state: #0", status.toString());
                    addActionError(getText("error.project.status.cannotAddStart", Lists.newArrayList(status.getName())));
                    return;
                }
            }
        }

        logger.debug("SaveStatusAction.validateInput: Validation passed: #0", status );
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {


        if (status.isStart() && !statusService.canAddAsStartState()) {
            if (!status.isValidID()) {//condition for new status
                logger.error("SaveStatusAction.executeImpl: Unable to add status as start state: #0", status.toString());
                addActionError(getText("error.project.status.cannotAddStart", Lists.newArrayList(status.getName())));
                return ERROR;
            }

        }

        logger.debug("SaveQuestionStatusAction.executeImpl: #0", status.toString());
        statusService.saveOrUpdate(status);
        return null;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }
}

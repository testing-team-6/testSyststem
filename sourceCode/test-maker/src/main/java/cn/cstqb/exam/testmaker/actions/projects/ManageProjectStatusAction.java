package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import com.google.common.collect.Lists;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/25
 * Time: 18:48
 */
public class ManageProjectStatusAction extends BaseProjectAction{
    private ProjectStatus status;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (status==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.projectStatus"))));
            return;
        }

        if (!status.validate()) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.status.name"))));
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
        /*
         * The default action is save/update status
         */


        if (!projectStatusService.canAddStartStatus() && status.isStart() && !status.isValidID()) {//condition for new status
            logger.error("ManageProjectStatusAction.executeImpl: Unable to save status [#0]. There should be only 1 project status with isStart=true", status.getName());
            addActionError(getText("error.project.status.cannotAddStart", Lists.newArrayList(status.getName())));
            return ERROR;
        }
        logger.info("ManageProjectStatusAction.executeImpl: Trying to save #0", status.toString() );
        projectStatusService.saveOrUpdate(status);
        return null;
    }


    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    private boolean isInputValid() {
        validateInput();
        return !hasActionErrors();
    }

    private boolean exists() {
        return projectStatusService.findByName(status.getName()) !=null;
    }


    public String delete() {
        if (!isInputValid()|| !status.isValidID()) {
            logger.error("ManageProjectStatusAction.delete: invalid status provided: #0", Objects.toString(status) );
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.projectStatus"), getText("message.project.status.requiredFields"))));
            return Constants.RESULT_FORM_INVALID;
        }



        if (!exists()) {
            logger.error("ManageProjectStatusAction.executeImpl: The status does not exist: #0", status.getName());
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(status.getName())));
            return Constants.RESULT_NOT_FOUND;
        }

        try {
            projectStatusService.delete(status);
        } catch (Exception e) {
            logger.error("ManageProjectStatusAction.delete: Exception occurred.",e );
            addActionError(getText("error.exception", Lists.newArrayList(e.getMessage())));
            return Constants.RESULT_EXCEPTION;
        }

        return SUCCESS;
    }
}

package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.services.IQuestionStatusService;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/27
 * Time: 21:45
 */
abstract class BaseQuestionStatusAction extends BaseAction {
    protected IQuestionStatusService statusService;

    protected BaseQuestionStatusAction() {
        super();
        statusService = injector.getInstance(IQuestionStatusService.class);
    }

    protected boolean validateStatus(QuestionStatus status) {
        if (status==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question.status"))));
            return false;
        }
        if (!status.validate()) {
            logger.error("BaseQuestionStatusAction.validateStatus: invalid status: #0", status.toString() );
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.name"))));
            return false;
        }
        return true;
    }
}

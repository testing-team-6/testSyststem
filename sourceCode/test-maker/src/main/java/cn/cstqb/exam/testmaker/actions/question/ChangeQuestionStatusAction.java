package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/27
 * Time: 22:53
 */
public class ChangeQuestionStatusAction extends BaseQuestionAction {

    private int questionId;
    private int statusId;

    private Question question;
    private QuestionStatus nextStatus;

    @Override
    public void validateInput() {
        if (questionId<1 || statusId<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(questionId)));
        }

        question = questionService.findQuestion(questionId);
        if (question == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(questionId, getText("label.entity.question"))));
            return;
        }

        nextStatus = statusService.findStatus(statusId);
        if (nextStatus == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(questionId, getText("label.entity.question.status"))));
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
        logger.debug("ChangeQuestionStatusAction.executeImpl: Attempting to change question #0 to status #1", questionId, nextStatus.getName() );

        if (question.getStatus().equals(nextStatus)) {
            addActionError(getText("message.question.status.notChanged", Lists.newArrayList(question.getStatus().getName())));
            addFieldError("current status id", "same as next status");
            return Constants.RESULT_NOT_MODIFIED;
        }

        question.setStatus(nextStatus);
        questionService.saveOrUpdate(question);
        return null;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public QuestionStatus getStatus() {
        return nextStatus;
    }
}

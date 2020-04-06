package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.actions.projects.current.BaseCurrentProjectAction;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.services.IQuestionStatusService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/27
 * Time: 22:18
 */
public class BatchUpdateInitialStatusAction extends BaseCurrentProjectAction {
    @Inject private IQuestionStatusService statusService;
    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private int targetStatusId;
    private QuestionStatus targetStatus;
    private int count;

    public BatchUpdateInitialStatusAction() {
        super();
        injector.injectMembers(this);
    }

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
        if (hasActionErrors()) {
            return;
        }

        //make sure only facilitator can do this
        if (!sessionProject.getFacilitator().getUsername().equals(sessionUser.getUsername())) {
            addActionError(getText("error.question.batch.update.initial.noAccess"));
            return;
        }

        if (targetStatusId < 1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(targetStatusId)));
            return;
        }

        targetStatus = statusService.findStatus(targetStatusId);
        if (targetStatus == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.question.status"))));
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
        List<Question> initialQuestions = questionService.find(sessionProject, statusService.findStartStatus());
        logger.debug("BatchUpdateInitialStatusAction.executeImpl: #0 questions with initial status", initialQuestions.size() );

        for (Question question : initialQuestions) {
            question.setStatus(targetStatus);
            questionService.saveOrUpdate(question);
            count++;
        }
        return null;
    }

    public void setTargetStatusId(int targetStatusId) {
        this.targetStatusId = targetStatusId;
    }

    public int getCount() {
        return count;
    }
}

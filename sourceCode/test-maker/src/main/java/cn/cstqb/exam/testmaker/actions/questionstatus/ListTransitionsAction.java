package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusAccessRole;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/27
 * Time: 22:18
 */
public class ListTransitionsAction extends BaseQuestionStatusAction {
    private List<QuestionStatus> transitions;
    private int statusId;
    private QuestionStatusAccessRole accessRole;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (statusId<1) {
            logger.error("ListTransitionsAction.validateInput: invalid status id: #0", statusId );
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(statusId)));
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
        transitions = statusService.findTransitionalStates(statusId);
/*        if (accessRole != null) {
            Collection<QuestionStatus> filtered = Collections2.filter(transitions, new Predicate<QuestionStatus>() {
                @Override
                public boolean apply(@Nullable QuestionStatus input) {
                    if (input == null) {
                        return false;
                    }
                    switch (accessRole) {
                        case AUTHOR:
                            if(input.isAccessibleByAuthor()) return true;
                            break;
                        case REVIEWER:
                            if(input.isAccessibleByReviewer()) return true;
                            break;
                        case QA:
                            if(input.isAccessibleByQualityAdmin()) return true;
                            break;
                        case FACILITATOR:
                            if(input.isAccessibleByFacilitator()) return true;
                            break;
                    }
                    return false;
                }
            });

            transitions = new ArrayList<>(filtered);
        }*/

        return null;
    }

    public List<QuestionStatus> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<QuestionStatus> transitions) {
        this.transitions = transitions;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setAccessRole(QuestionStatusAccessRole accessRole) {
        this.accessRole = accessRole;
    }
}

package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransition;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransitionPK;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 16:53
 */
public interface QuestionStatusTransitionDao extends GenericDao<QuestionStatusTransition, QuestionStatusTransitionPK> {
    /**
     *
     * @param status
     * @return
     */
    List<QuestionStatusTransition> findTransitions(QuestionStatus status);

    /**
     * Lists all possible transitional statuses for this statue. Sequence is ignored
     * @param status
     * @return
     */
    java.util.Set<QuestionStatus> findTransitionalStatus(@Nonnull QuestionStatus status);

    List<QuestionStatus> findTransitionalStatus(int statusId);

    /**
     * Check if the provided transition exists in DB
     * @param status
     * @param nextStatus
     * @return
     */
    boolean existsTransition(QuestionStatus status, QuestionStatus nextStatus);

    /**
     * <b>Note</b> The sequence is ignored in this method
     * @param status
     * @param statuses
     */
    void saveTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses);

    /**
     *
     * @param status
     * @param statuses
     */
    void deleteTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses);

    /**
     * Delete any transition that is associated with this status either in current field or in next status field
     * @param status The status whose transitions will be deleted.
     */
    void deleteTransitions(QuestionStatus status);

    /**
     * Clear all transition mappings for the given field
     * @param status
     */
    void clearTransitions(QuestionStatus status);
}

package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransition;
import cn.cstqb.exam.testmaker.exceptions.EntityNotFoundException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/18
 * Time: 22:54
 */
public interface IQuestionStatusService {
	/**
     * Create or update a question status
     * @param status
     */
    void saveOrUpdate(QuestionStatus status);

    /**
     * Delete a question status
     * @param status
     */
    void delete(QuestionStatus status);

    /**
     * Find a question status for the given id
     * @param id
     * @return
     */
    QuestionStatus findStatus(int id);

    /**
     * Finds the the first question status in the question status transition workflow.
     * @return QuestionStatus
     */
    QuestionStatus findStartStatus();

    /**
     * Find a question status for the given name
     * @param name
     * @return
     */
    QuestionStatus findStatus(String name);

    /**
     *
     * @return
     */
    boolean canAddAsStartState();
    /**
     *
     * @return
     */
    List<QuestionStatus> findAllStatuses();

    /**
     * Find a list of statuses the given status can transit to.
     * @param status The status to check. Can be null.
     * @return A list of transitions for this status. <b>Note:</b> all transitions will be returned if the status is null
     */
    List<QuestionStatusTransition> findTransitions(@Nullable QuestionStatus status);

    /**
     * Finds all transitions in this system.
     * @return
     */
    List<QuestionStatusTransition> findAllTransitions();
    /**
     *
     * @param status
     * @param nextStatus
     * @return
     */
    boolean existsTransition(QuestionStatus status, QuestionStatus nextStatus);

    /**
     * @param transition
     */
    void saveTransition(QuestionStatusTransition transition);

    /**
     *
     * @param transition
     */
    void removeTransition(QuestionStatusTransition transition) ;

    java.util.Set<QuestionStatus> findTransitionalStates(QuestionStatus status);

    void saveTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses);

    /**
     *  @param status
     * @param statuses
     */
    void deleteTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses);

    List<QuestionStatus> findTransitionalStates(int statusId);

    /**
     * Clear all mappings for the status
     * @param status
     */
    void clearTransitions(QuestionStatus status);
}

package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.QuestionStatusDao;
import cn.cstqb.exam.testmaker.dao.QuestionStatusTransitionDao;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransition;
import cn.cstqb.exam.testmaker.services.IQuestionStatusService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/18
 * Time: 22:57
 */
public class QuestionStatusServiceImpl implements IQuestionStatusService {
	private QuestionStatusDao dao;
	private QuestionStatusTransitionDao transitionDao;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	public QuestionStatusServiceImpl(QuestionStatusDao dao,
			QuestionStatusTransitionDao transitionDao) {
		this.dao = dao;
		this.transitionDao = transitionDao;
	}

    private boolean isValidStatus(QuestionStatus status) {
        return status!=null && status.validate() && status.isValidID();
    }

    /**
     * Create or update a question status
     * @param status
     */
	@Override
	public void saveOrUpdate(QuestionStatus status) {
		Preconditions.checkArgument(status != null , "Invalid status. Provided: %s", status);
		Preconditions.checkArgument(!Strings.isNullOrEmpty(status.getName()),"The content of status cannot be null or empty.");
        QuestionStatus persisted = dao.findById(status.getId());
		if (persisted == null) {
			dao.create(status);
		} else {
            persisted.setName(status.getName());
            persisted.setStart(status.isStart());
            persisted.setFinish(status.isFinish());
            persisted.setAccessibleByAuthor(status.isAccessibleByAuthor());
            persisted.setAccessibleByFacilitator(status.isAccessibleByFacilitator());
            persisted.setAccessibleByQualityAdmin(status.isAccessibleByQualityAdmin());
            persisted.setAccessibleByReviewer(status.isAccessibleByReviewer());
			dao.update(persisted);
		}
	}

	/**
     * Delete a question status
     * @param status
     */
	@Override
	public void delete(QuestionStatus status) {
		Preconditions.checkArgument(isValidStatus(status),"Invalid status. Provided: %s", status);
        QuestionStatus persisted = dao.findById(status.getId());
        if (persisted==null) {
            logger.error("QuestionStatusServiceImpl.delete: FAILED. Status does not exist. {}", status );
            return;
        }
        transitionDao.deleteTransitions(status);
        dao.delete(persisted);
	}

	/**
     * Find a question status for the given id
     * @param id
     * @return
     */
	@Override
	public QuestionStatus findStatus(int id) {
		return dao.findById(id);
	}

    /**
     * Finds the the first question status in the question status transition workflow.
     *
     * @return QuestionStatus
     */
    @Override
    public QuestionStatus findStartStatus() {
        return dao.findStart();
    }

    /**
     * Find a question status for the given name
     * @param name
     * @return
     */
	@Override
	public QuestionStatus findStatus(String name) {
		return dao.findByName(name);
	}

    /**
     * @return
     */
    @Override
    public List<QuestionStatus> findAllStatuses() {
        return dao.findAll();
    }

    /**
     * @return
     */
    @Override
    public boolean canAddAsStartState() {
        return dao.canAddStartStatus();
    }

    /**
	 * Find a list of statuses the given status can transit to.
	 *
	 * @param status
	 * @return
	 */
	@Override
	public List<QuestionStatusTransition> findTransitions(QuestionStatus status) {
        if (status==null) {
            logger.info("QuestionStatusServiceImpl.findTransitions: The query status is null. Returning all transitions." );
            return transitionDao.findAll();
        }

        /*
         * The status now should be Non-Null and the ID should valid
         */
        Preconditions.checkArgument(status.getId() > 0 && !Strings.isNullOrEmpty(status.getName()),
                "The status id or name is not valid. Provided: %s", status);
        List<QuestionStatusTransition> transitions = transitionDao.findTransitions(status);
        logger.debug("QuestionStatusServiceImpl.findTransitions: {} transitions found for status [{}]", transitions != null ? transitions.size() : 0, status.getName());
        return transitions;
    }

    @Override
    public java.util.Set<QuestionStatus> findTransitionalStates(QuestionStatus status) {
        Preconditions.checkArgument(status!=null && status.validate() && status.isValidID(), "Invalid status provided. %s", status);
        return transitionDao.findTransitionalStatus(status);
    }

    @Override
    public List<QuestionStatus> findTransitionalStates(int statusId) {
        Preconditions.checkArgument(statusId>0,"ID must be greater than zero.");
        return transitionDao.findTransitionalStatus(statusId);
    }

    /**
     * @param status
     * @param nextStatus
     * @return
     */
    @Override
    public boolean existsTransition(QuestionStatus status, QuestionStatus nextStatus) {
        Preconditions.checkArgument(status!=null && nextStatus!=null);
        Preconditions.checkState(status.isValidID() && nextStatus.isValidID(), "ID must be provided for the transition check.");
        Preconditions.checkState(!status.getId().equals(nextStatus.getId()), "A status cannot transit to itself. Given: %s", status);
        return transitionDao.existsTransition(status, nextStatus);
    }

    /**
     * @param transition
     */
    @Override
    public void saveTransition(QuestionStatusTransition transition) {
        Preconditions.checkArgument(transition!=null);
        Preconditions.checkState(transition.getTransitionPK()!=null,"ID is required for the transition to work. The ID is the combination of two status ids.");
        logger.debug("QuestionStatusServiceImpl.saveTransition: saving transition {}", transition );
        QuestionStatusTransition persisted = transitionDao.findById(transition.getTransitionPK());
        if (persisted == null) {
            transitionDao.create(transition);
        } else {
            transitionDao.update(transition);
        }
    }

    @Override
    public void saveTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses) {
        Preconditions.checkNotNull(status);
        Preconditions.checkArgument(status.validate() && status.isValidID(), "Status is invalid: %s", status);
        Preconditions.checkNotNull(statuses);
        transitionDao.clearTransitions(status);

        transitionDao.saveTransitionalStates(status, statuses);
    }

    /**
     * Clear all mappings for the status
     *
     * @param status
     */
    @Override
    public void clearTransitions(QuestionStatus status) {
        Preconditions.checkArgument(status!=null && status.validate() && status.isValidID(),"Status is invalid: %s", status);
        transitionDao.clearTransitions(status);
    }

    /**
     * @param status
     * @param statuses
     */
    @Override
    public void deleteTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses) {
        Preconditions.checkArgument(status!=null && status.validate() && status.isValidID(),"Status is invalid: %s", status);
        Preconditions.checkNotNull(statuses);
        transitionDao.deleteTransitionalStates(status, statuses);
    }

    /**
     * @param transition
     */
    @Override
    public void removeTransition(QuestionStatusTransition transition)  {
        Preconditions.checkArgument(transition!=null);
        Preconditions.checkState(transition.getTransitionPK()!=null,"ID is required for the transition to work. The ID is the combination of two status ids.");
        QuestionStatusTransition persisted = transitionDao.findById(transition.getTransitionPK());
        if (persisted != null) {
            transitionDao.delete(persisted);
        } else {
            logger.error("QuestionStatusServiceImpl.removeTransition: Transition does not exist in DB {}", transition );
        }
    }

    /**
     * Finds all transitions in this system.
     *
     * @return
     */
    @Override
    public List<QuestionStatusTransition> findAllTransitions() {
        return transitionDao.findAll();
    }
}

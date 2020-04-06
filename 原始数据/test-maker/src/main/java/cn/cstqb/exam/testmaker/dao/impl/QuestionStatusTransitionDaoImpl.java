package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.QuestionStatusTransitionDao;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransition;
import cn.cstqb.exam.testmaker.entities.QuestionStatusTransitionPK;
import com.google.common.base.Preconditions;
import com.google.inject.persist.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/5
 * Time: 2:30
 */
public class QuestionStatusTransitionDaoImpl extends GenericJpaDaoImpl<QuestionStatusTransition, QuestionStatusTransitionPK>
    implements QuestionStatusTransitionDao{
    private boolean isValidStatus(QuestionStatus status) {
        return status!=null && status.validate() && status.isValidID();
    }

    @Transactional
    @Override
    public void create(QuestionStatusTransition entity) {
        Objects.requireNonNull(entity, "Entity object must not be null");
        QuestionStatusTransitionPK pk = entity.getTransitionPK();
        QuestionStatusTransition _saved = super.findById(pk);
        if (_saved == null) {
            super.create(entity);
        } else {
            logger.warn("Entity already saved. {}", entity);
            return;
        }
    }

    @Transactional
    public void clearTransitions(QuestionStatus status) {
        logger.debug("QuestionStatusTransitionDaoImpl.clearTransitions: clearing transitions for {}", status );
        EntityManager em=provider.get();
        int rows=em.createQuery("DELETE FROM QuestionStatusTransition t WHERE t.id.status =:status")
                .setParameter("status", status)
                .executeUpdate();
        logger.debug("QuestionStatusTransitionDaoImpl.clearTransitions: {} transitions deleted", rows );
    }

    @Override
    @Transactional
    public void saveTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses) {
        EntityManager em=provider.get();
        int count=0;

        for (QuestionStatus nextStatus : statuses) {
            QuestionStatusTransitionPK pk = new QuestionStatusTransitionPK(status, nextStatus);
            boolean persistedAlready=em.contains(nextStatus);
            logger.info("QuestionStatusTransitionDaoImpl.saveTransitionalStates: next status persisted: {}. {}", persistedAlready, nextStatus);
            if (persistedAlready) {
                logger.warn("QuestionStatusTransitionDaoImpl.saveTransitionalStates: The target status {} is already mapped to current status: {}",
                        nextStatus.getName(), status.getName());
                continue;
            }

            //The sequence is not set yet. This will be implemented on demand.
            QuestionStatusTransition transition = new QuestionStatusTransition(pk);
            em.merge(transition);
            count++;
        }
        logger.info("QuestionStatusTransitionDaoImpl.saveTransitionalStates: {} transitions saved!", count );
    }

    @Override
    @Transactional
    public void deleteTransitionalStates(QuestionStatus status, Set<QuestionStatus> statuses) {
        EntityManager em=provider.get();
        int count=0;
        for (QuestionStatus nextStatus : statuses) {
            QuestionStatusTransition persisted = findById(new QuestionStatusTransitionPK(status, nextStatus));
            if (persisted==null) {
                logger.warn("QuestionStatusTransitionDaoImpl.deleteTransitionalStates: SKIPPED. The target status [#1] is NOT mapped to current status yet: #0",
                        status.getName(), nextStatus.getName() );
                continue;
            }
            em.remove(nextStatus);
            count++;
        }
        logger.info("QuestionStatusTransitionDaoImpl.deleteTransitionalStates: {} transitions deleted!", count );
    }

    /**
     * Delete any transition that is associated with this status either in current field or in next status field
     *
     * @param status The status whose transitions will be deleted.
     */
    @Override
    @Transactional
    public void deleteTransitions(QuestionStatus status) {
        Preconditions.checkArgument(isValidStatus(status),"Invalid question status: %s", status);
        EntityManager em = provider.get();

        if (em.contains(status)) {
            logger.debug("QuestionStatusTransitionDaoImpl.deleteTransitions: Status already in PersistenceContext. {}", status.getId());
        } else {
            logger.info("QuestionStatusTransitionDaoImpl.deleteTransitions: Status NOT FOUND in PersistenceContext. Checking DB {}", status.getId());
            status = em.find(QuestionStatus.class, status.getId());
        }

        if (status==null) {
            logger.error("QuestionStatusTransitionDaoImpl.deleteTransitions: Status NOT FOUND IN DB: {}", status );
            return;
        }
        logger.info("QuestionStatusTransitionDaoImpl.deleteTransitions: DELETING {}", status );
        int rows=em.createQuery("DELETE FROM QuestionStatusTransition t WHERE t.transitionPK.status = :status OR t.transitionPK.nextStatus = :status ")
                .setParameter("status", status)
                .executeUpdate();
        logger.info("QuestionStatusTransitionDaoImpl.deleteTransitions: Rows deleted: {}", rows );
    }

    @Override
    @Transactional
    public List<QuestionStatusTransition> findTransitions(QuestionStatus status) {
        EntityManager em = provider.get();
        List<QuestionStatusTransition> transitions =
                em.createQuery("SELECT t FROM QuestionStatusTransition t WHERE t.transitionPK.status = :status ORDER BY t.sequence ASC ", QuestionStatusTransition.class)
                .setParameter("status", status)
                .getResultList();
        return transitions;
    }

    /**
     * Lists all possible transitional statuses for this statue. Sequence is ignored
     *
     * @param status
     * @return
     */
    @Override
    public Set<QuestionStatus> findTransitionalStatus(@Nonnull QuestionStatus status) {
        EntityManager em = provider.get();
        List<QuestionStatus> statuses=
                em.createQuery("SELECT DISTINCT t.transitionPK.nextStatus FROM QuestionStatusTransition t WHERE t.transitionPK.status=:status", QuestionStatus.class)
                        .setParameter("status", status)
                        .getResultList();
        logger.debug("QuestionStatusTransitionDaoImpl.findTransitionalStatus: transitional statuses found: {}", statuses.size() );
        return new LinkedHashSet<>(statuses);
    }

    @Override
    public List<QuestionStatus> findTransitionalStatus(int statusId) {
        EntityManager em = provider.get();
        List<QuestionStatus> statuses=
                em.createQuery("SELECT t.transitionPK.nextStatus FROM QuestionStatusTransition t WHERE t.transitionPK.status.id=:statusId", QuestionStatus.class)
                        .setParameter("statusId", statusId)
                        .getResultList();
        logger.debug("QuestionStatusTransitionDaoImpl.findTransitionalStatus: transitional statuses found: {}", statuses.size() );
        return statuses;
    }

    /**
     * Check if the provided transition exists in DB
     *
     * @param status
     * @param nextStatus
     * @return
     */
    @Override
    @Transactional
    public boolean existsTransition(QuestionStatus status, QuestionStatus nextStatus) {
        EntityManager em=provider.get();
        TypedQuery<Long> query = em.createQuery("SELECT count(t) FROM QuestionStatusTransition t WHERE t.transitionPK.status =:status and t.transitionPK.nextStatus = :nextStatus ", Long.class);
        query.setParameter("status", status);
        query.setParameter("nextStatus", nextStatus);
        Long result= query.getSingleResult();
        logger.debug("QuestionStatusTransitionDaoImpl.existsTransition: {}", result );
        return result > 0;
    }

}


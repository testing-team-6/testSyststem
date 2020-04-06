package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.ReviewCommentDao;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:49
 */
public class ReviewCommentDaoImpl extends GenericJpaDaoImpl<ReviewComment, Integer> implements ReviewCommentDao {
    /**
     * Finds all review comments for the given question
     *
     * @param question
     * @return
     */
    @Override
    @Transactional
    public List<ReviewComment> findAll(Question question) {
        EntityManager em = provider.get();
        return em.createQuery("SELECT c FROM ReviewComment c WHERE c.question = :question ORDER BY c.updatedOn DESC", ReviewComment.class)
                .setParameter("question", question)
                .getResultList();
    }

    /**
     * Finds review comments for the question
     *
     * @param question      question whose reviews will be searched
     * @param finalComments <b>True</b> to retrieve QA comments only; <b>false</b> to retrieve non-QA comments
     * @return Review comments related with the subject question
     */
    @Override
    public List<ReviewComment> find(Question question, boolean finalComments) {
        EntityManager em = provider.get();
        return em.createQuery("SELECT c FROM ReviewComment c WHERE c.question = :question AND c.isFinalReview =:finalReview ORDER BY c.updatedOn ASC", ReviewComment.class)
                .setParameter("question", question)
                .setParameter("finalReview", finalComments)
                .getResultList();
    }

    /**
     * Finds all review comments made by the given user on the given question
     *
     * @param question
     * @param reviewer
     * @return
     */
    @Override
    @Transactional
    public List<ReviewComment> find(Question question, User reviewer) {
        EntityManager em = provider.get();
        return em.createQuery("SELECT c FROM ReviewComment c WHERE c.question = :question AND c.reviewer =:reviewer", ReviewComment.class)
                .setParameter("question", question)
                .setParameter("reviewer", reviewer)
                .getResultList();
    }
}

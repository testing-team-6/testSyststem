package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.QuestionChoiceDao;
import cn.cstqb.exam.testmaker.dao.ReviewCommentDao;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.ReviewComment;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:49
 */
public class QuestionChoiceDaoImpl extends GenericJpaDaoImpl<QuestionChoice, Integer> implements QuestionChoiceDao {
    @Override
    public List<QuestionChoice> findQuestionChoices(int questionId) {
        EntityManager em=provider.get();
        return em.createQuery("SELECT c FROM QuestionChoice c WHERE c.question.id = :questionId", QuestionChoice.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}

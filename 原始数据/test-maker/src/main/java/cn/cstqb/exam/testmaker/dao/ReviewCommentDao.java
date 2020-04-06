package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import cn.cstqb.exam.testmaker.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:48
 */
public interface ReviewCommentDao extends GenericDao<ReviewComment, Integer> {

    /**
     * Finds all review comments for the given question
     * @param question
     * @return
     */
    List<ReviewComment> findAll(Question question);

    /**
     * Finds review comments for the question
     * @param question question whose reviews will be searched
     * @param finalComments <b>True</b> to retrieve QA comments only; <b>false</b> to retrieve non-QA comments
     * @return Review comments related with the subject question
     */
    List<ReviewComment> find(Question question, boolean finalComments);

    /**
     * Finds all review comments made by the given user on the given question
     * @param question
     * @param reviewer
     * @return
     */
    List<ReviewComment> find(Question question, User reviewer);
}

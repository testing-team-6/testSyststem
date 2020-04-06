package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.ReviewComment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/19
 * Time: 0:41
 */
public interface IReviewCommentService {
    /**
     * Create or update a review comment
     * @param comment
     */
    void saveOrUpdate(ReviewComment comment);

    /**
     * Delete a comment
     * @param comment
     */
    void delete(ReviewComment comment);

    ReviewComment find(int id);

    List<ReviewComment> getReviewComments(Question question);
    /**
     * Find all final review comments for the given question
     * @param question
     * @return
     */
    List<ReviewComment> getQAComments(Question question);
}

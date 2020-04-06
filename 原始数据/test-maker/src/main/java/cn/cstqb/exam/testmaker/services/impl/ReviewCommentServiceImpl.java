package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.ReviewCommentDao;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.ReviewComment;

import cn.cstqb.exam.testmaker.services.IReviewCommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.*;

import javax.inject.Inject;

import java.util.List;

public class ReviewCommentServiceImpl implements
        IReviewCommentService {
	private Logger Logger = LoggerFactory.getLogger(getClass());
	private ReviewCommentDao reviewCommentDao;

	@Inject
	public ReviewCommentServiceImpl(ReviewCommentDao reviewCommentDao) {
		this.reviewCommentDao = reviewCommentDao;
	}

	/**
	 * Create or update a review comment
	 *
	 * @param comment
	 */
	@Override
	public void saveOrUpdate(ReviewComment comment) {
		checkArgument(comment != null, "Comment cannot be null.");
        ReviewComment persisted = reviewCommentDao.findById(comment.getId());
		if (persisted == null) {
			reviewCommentDao.create(comment);
		} else {
			reviewCommentDao.update(comment);
		}
	}

	/**
	 * Delete a comment
	 *
	 * @param comment
	 */
	@Override
	public void delete(ReviewComment comment) {
		checkArgument(comment != null && comment.getId() > 0,
				"Invalid comment. id : %s", comment.getId());
		reviewCommentDao.delete(comment);
	}

    @Override
    public ReviewComment find(int id) {
        checkArgument(id>0,"Comment ID must be greater than zero! Given: %s", id);
        return reviewCommentDao.findById(id);
    }

    @Override
	public List<ReviewComment> getReviewComments(Question question) {
        checkNotNull(question);
        checkState(question.validate() && question.isValidID());
		return reviewCommentDao.find(question, false);
	}

    /**
     * Find all final review comments for the given question
     *
     * @param question
     * @return
     */
    @Override
    public List<ReviewComment> getQAComments(Question question) {
        checkNotNull(question);
        checkState(question.validate() && question.isValidID());
        return reviewCommentDao.find(question, true);
    }
}

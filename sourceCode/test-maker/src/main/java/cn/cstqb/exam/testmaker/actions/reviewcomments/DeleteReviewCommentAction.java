package cn.cstqb.exam.testmaker.actions.reviewcomments;

import cn.cstqb.exam.testmaker.entities.ReviewComment;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/24
 * Time: 16:29
 */
public class DeleteReviewCommentAction extends BaseReviewCommentAction {
    private int id;
    private String operatorUserName;
    private ReviewComment review;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (id<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(id)));
            return;
        }
        if (Strings.isNullOrEmpty(operatorUserName)) {
            addActionError(getText("message.question.review.delete.missingOperator"));
            return;
        }
        review = commentService.find(id);
        if (review == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.reviewComment"))));
            return;
        }
        if (!review.getReviewer().getUsername().equals(operatorUserName)) {
            addActionError(getText("message.question.review.delete.accessDenied", Lists.newArrayList(review.getReviewer().getFullName())));
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
        logger.debug("DeleteReviewCommentAction.executeImpl: Deleting review comment #0", id );

        commentService.delete(review);
        return null;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }
}

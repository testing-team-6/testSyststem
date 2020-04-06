package cn.cstqb.exam.testmaker.actions.reviewcomments;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.struts2.json.annotations.JSON;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/24
 * Time: 16:23
 */
public class SaveReviewCommentAction extends BaseReviewCommentAction {
    private ReviewComment comment;
    private String reviewerName;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (comment == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.reviewComment"))));
        }
//        if (Strings.isNullOrEmpty(comment.getContent()) || comment.getQuestion()==null) {
//            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.reviewComment"), getText("message.question.reviewComment.requiredFields"))));
//        }
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("SaveReviewCommentAction.executeImpl: saving review comment: #0", comment );
        if (comment.getReviewer() == null) {
            User loggedUser = getLoggedInUser();
            if (loggedUser == null) {
                addActionError(getText("error.user.auth.notLoggedIn"));
                return Constants.RESULT_USER_NOT_AUTHENTICATED;
            }
            comment.setReviewer(loggedUser);
        }
        commentService.saveOrUpdate(comment);
        return null;
    }

    public void setComment(ReviewComment comment) {
        this.comment = comment;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}

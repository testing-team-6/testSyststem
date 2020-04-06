package cn.cstqb.exam.testmaker.actions.reviewcomments;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import com.google.common.collect.Lists;
import org.apache.struts2.json.annotations.JSON;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/23
 * Time: 23:45
 */
public class ListReviewCommentsAction extends BaseReviewCommentAction {
    private int id;
    private boolean qaComments;
    private List<ReviewComment> comments;

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
        logger.debug("ListReviewCommentsAction.executeImpl: Listing all review comments for question: #0", id );
        Question question = questionService.findQuestion(id);
        if (question == null) {
            addActionError(getText("error.question.not.found", Lists.newArrayList(id)));
            return Constants.RESULT_FORM_INVALID;
        }
        if (qaComments) {
            this.comments = commentService.getQAComments(question);
        } else {
            this.comments = commentService.getReviewComments(question);
        }
        return null;
    }

    public List<ReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<ReviewComment> comments) {
        this.comments = comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQaComments(boolean qaComments) {
        this.qaComments = qaComments;
    }
}

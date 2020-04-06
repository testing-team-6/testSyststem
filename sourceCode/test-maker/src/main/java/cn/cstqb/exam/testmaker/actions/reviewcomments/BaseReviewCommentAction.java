package cn.cstqb.exam.testmaker.actions.reviewcomments;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IReviewCommentService;
import com.google.common.collect.Lists;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/23
 * Time: 23:32
 */
abstract class BaseReviewCommentAction extends BaseAction {
    @Inject protected IQuestionService questionService;
    @Inject protected IReviewCommentService commentService;
    protected User reviewer;
    protected BaseReviewCommentAction() {
        super();
        injector.injectMembers(this);
    }

    protected boolean validateReviewComment(ReviewComment comment) {
        if (comment == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.reviewComment"))));
        }
        if (!comment.validate()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.reviewComment"), getText("message.question.reviewComment.requiredFields"))));
            return false;
        }
        return true;
    }
}

package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionStatusService;
import cn.cstqb.exam.testmaker.services.IReviewCommentService;
import com.google.common.collect.Lists;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/5/30
 * Time: 8:58
 */
public class LoadProjectQuestionDetails extends BaseCurrentProjectAction {
    @Inject protected IQuestionStatusService statusService;
    @Inject protected IReviewCommentService commentService;
    @Inject protected IQuestionChoiceService choiceService;

    private int id;
    private List<QuestionChoice> choices;
    private List<ReviewComment> comments;
    private List<ReviewComment> qaComments;
    private Set<QuestionStatus> transitions;
    private Question question;

    @Override
    public void validateInput() {
        super.validateInput();
        if (id<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(id)));
        }
        question = questionService.findQuestion(id);
        if (question == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.question"))));
        }

        if (!question.getProject().getId().equals(sessionProject.getId())) {
            addActionError(getText("error.project.current.question.wrongId", Lists.newArrayList(id, sessionProject.getName())));
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
        logger.debug("LoadProjectQuestionDetails.executeImpl: Loading question details for #0", id );

        this.choices = choiceService.findQuestionChoices(id);
        this.comments = commentService.getReviewComments(question);
        this.qaComments = commentService.getQAComments(question);
        this.transitions = statusService.findTransitionalStates(question.getStatus());
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public List<ReviewComment> getComments() {
        return comments;
    }

    public List<ReviewComment> getQaComments() {
        return qaComments;
    }

    public Set<QuestionStatus> getTransitions() {
        return transitions;
    }

    public Question getQuestion() {
        return question;
    }
}

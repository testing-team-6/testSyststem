package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;
import cn.cstqb.exam.testmaker.entities.ReviewComment;
import com.google.common.collect.Lists;
import org.apache.struts2.json.annotations.JSON;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/24
 * Time: 20:16
 */
public class ViewQuestionAction extends BaseQuestionAction {
    private int id;
    private List<QuestionChoice> choices;
    private List<ReviewComment> comments;
    private List<ReviewComment> qaComments;
    private Set<QuestionStatus> transitions;
    private Question question;

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
        logger.debug("ViewQuestionAction.executeImpl: Loading question details for #0", id );
        question = questionService.findQuestion(id);
        if (question == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.question"))));
            return Constants.RESULT_NOT_FOUND;
        }

        this.choices = choiceService.findQuestionChoices(id);
        this.comments = commentService.getReviewComments(question);
        this.qaComments = commentService.getQAComments(question);
        this.transitions = statusService.findTransitionalStates(question.getStatus());
        return null;
    }

    @JSON(serialize = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<ReviewComment> comments) {
        this.comments = comments;
    }

    public List<ReviewComment> getQaComments() {
        return qaComments;
    }

    public void setQaComments(List<ReviewComment> qaComments) {
        this.qaComments = qaComments;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public Set<QuestionStatus> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<QuestionStatus> transitions) {
        this.transitions = transitions;
    }
}

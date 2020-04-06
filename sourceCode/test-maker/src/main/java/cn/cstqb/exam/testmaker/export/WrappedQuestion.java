package cn.cstqb.exam.testmaker.export;

import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;

import java.util.List;

/**
 * <div>
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao <br>
 * Date: 2015/11/9 <br>
 * Time: 19:44 <br>
 * </div>
 */
public class WrappedQuestion {
    private Question question;
    private List<QuestionChoice> choices;

    public WrappedQuestion(Question question, List<QuestionChoice> choices) {
        this.question = question;
        this.choices = choices;
    }

    public WrappedQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }
}

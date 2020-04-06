package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:35
 */
@Entity
public class QuestionChoice extends AbstractBaseEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false,length = 10)
    private char choiceLabel;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isCorrectAnswer;

    @OneToMany(mappedBy = "choice")
    private Set<QuestionChoiceImage> images;

    public QuestionChoice(Question question, char choiceLabel, String content) {
        this.question = question;
        this.choiceLabel = choiceLabel;
        this.content = content;
    }

    public QuestionChoice(Question question, String content, Boolean isCorrectAnswer) {
        this.question = question;
        this.content = content;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public QuestionChoice(Question question, String content) {
        this.question = question;
        this.content = content;
    }

    public QuestionChoice() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setIsCorrectAnswer(Boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public Set<QuestionChoiceImage> getImages() {
        return images;
    }

/*    public void setImages(Set<QuestionChoiceImage> images) {
        this.images = images;
    }*/

    public char getChoiceLabel() {
        return choiceLabel;
    }

    public void setChoiceLabel(char choiceLabel) {
        this.choiceLabel = choiceLabel;
    }

	@Override
	public boolean validate() {
		return !Strings.isNullOrEmpty(String.valueOf(choiceLabel))
				&& !Strings.isNullOrEmpty(content)
                && question!=null;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionChoice that = (QuestionChoice) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.question, that.question) &&
                Objects.equal(this.choiceLabel, that.choiceLabel) &&
                Objects.equal(this.content, that.content) &&
                Objects.equal(this.isCorrectAnswer, that.isCorrectAnswer) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, question, choiceLabel, content, isCorrectAnswer,
                createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("question", question)
                .add("choiceLabel", choiceLabel)
                .add("content", content)
                .add("isCorrectAnswer", isCorrectAnswer)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

}

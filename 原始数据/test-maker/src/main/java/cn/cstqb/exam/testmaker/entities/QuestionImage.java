package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/2
 * Time: 17:05
 */
@Entity
public class QuestionImage extends AbstractImageAttachment {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;

    public QuestionImage(Question question, String caption, String path) {
        this.question = question;
        this.caption = caption;
        this.path = path;
    }

    public QuestionImage() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionImage that = (QuestionImage) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.question, that.question) &&
                Objects.equal(this.caption, that.caption) &&
                Objects.equal(this.path, that.path) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, caption, path, createdOn, updatedOn);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("question", question.getId())
                .add("caption", caption)
                .add("path", path)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

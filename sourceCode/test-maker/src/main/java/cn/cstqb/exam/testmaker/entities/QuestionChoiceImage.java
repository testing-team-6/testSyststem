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
 * Time: 20:21
 */
@Entity
public class QuestionChoiceImage extends AbstractImageAttachment {
    @ManyToOne
    @JoinColumn(name = "QUESTION_CHOICE_ID", nullable = false)
    @JsonIgnore
    private QuestionChoice choice;

    public QuestionChoiceImage(QuestionChoice choice, String path) {
        this.choice = choice;
        this.path = path;
    }

    public QuestionChoiceImage() {
    }

    public QuestionChoice getChoice() {
        return choice;
    }

    public void setChoice(QuestionChoice choice) {
        this.choice = choice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionChoiceImage that = (QuestionChoiceImage) o;

        return Objects.equal(this.choice, that.choice) &&
                Objects.equal(this.caption, that.caption) &&
                Objects.equal(this.path, that.path) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(caption, path, id, createdOn, updatedOn);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("choice", choice.getId())
                .add("caption", caption)
                .add("path", path)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

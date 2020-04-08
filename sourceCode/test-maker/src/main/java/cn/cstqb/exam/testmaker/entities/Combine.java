package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//该类参照ProjectUser建成
@Entity
public class Combine extends AbstractBaseEntity{
    @ManyToOne
    @JoinColumn(name = "test_paper_id", nullable = false)
    private Paper paper;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Combine(Paper paper,Question question){
        this.paper = paper;
        this.question = question;
    }

    public Combine() {

    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
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

        Combine that = (Combine) o;

        return Objects.equal(this.paper, that.paper) &&
                Objects.equal(this.question, that.question) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paper, question, id, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("paper", paper)
                .add("question", question)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/4
 * Time: 18:50
 */
@Entity
public class QuestionStatusTransition extends AbstractEntity {

    @Id
    private QuestionStatusTransitionPK transitionPK;

    private Integer sequence;

    public QuestionStatusTransition(QuestionStatus status, QuestionStatus transition, int sequence) {
        this.transitionPK = new QuestionStatusTransitionPK(status, transition);
        this.sequence = sequence;
    }

    public QuestionStatusTransition(QuestionStatusTransitionPK transitionPK, Integer sequence) {
        this.transitionPK = transitionPK;
        this.sequence = sequence;
    }

    public QuestionStatusTransition(QuestionStatusTransitionPK transitionPK) {
        this.transitionPK = transitionPK;
    }

    public QuestionStatusTransition() {
    }
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public QuestionStatusTransitionPK getTransitionPK() {
        return transitionPK;
    }

    public void setTransitionPK(QuestionStatusTransitionPK transitionPK) {
        this.transitionPK = transitionPK;
    }

    @PrePersist
    private void setDefaults(){
        if (sequence == null) {
            this.sequence = 0;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionStatusTransition that = (QuestionStatusTransition) o;

        return Objects.equal(this.transitionPK, that.transitionPK) &&
                Objects.equal(this.sequence, that.sequence) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transitionPK, sequence, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("transitionPK", transitionPK)
                .add("sequence", sequence)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return false;
    }
}

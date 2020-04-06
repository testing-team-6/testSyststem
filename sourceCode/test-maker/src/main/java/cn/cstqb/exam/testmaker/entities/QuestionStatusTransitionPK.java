package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/4
 * Time: 18:59
 */
@Embeddable
public class QuestionStatusTransitionPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "status_id")
    private QuestionStatus status;

    @ManyToOne
    @JoinColumn(name = "next_status_id")
    private QuestionStatus nextStatus;

    public QuestionStatusTransitionPK(QuestionStatus status, QuestionStatus nextStatus) {
        this.status = status;
        this.nextStatus = nextStatus;
    }

    public QuestionStatusTransitionPK() {
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public QuestionStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(QuestionStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(status, nextStatus);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final QuestionStatusTransitionPK other = (QuestionStatusTransitionPK) obj;
        return Objects.equal(this.status, other.status) && Objects.equal(this.nextStatus, other.nextStatus);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("status", status)
                .add("nextStatus", nextStatus)
                .toString();
    }


}

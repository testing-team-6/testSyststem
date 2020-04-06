package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 19:40
 */
@Entity
public class QuestionStatus extends AbstractEntityStatus {
    @JsonIgnore
    private boolean accessibleByFacilitator;
    @JsonIgnore
    private boolean accessibleByAuthor;
    @JsonIgnore
    private boolean accessibleByReviewer;
    @JsonIgnore
    private boolean accessibleByQualityAdmin;

    public QuestionStatus(String name) {
        super(name);
    }

    public QuestionStatus() {
    }

    public boolean isAccessibleByFacilitator() {
        return accessibleByFacilitator;
    }

    public void setAccessibleByFacilitator(boolean accessibleByFacilitator) {
        this.accessibleByFacilitator = accessibleByFacilitator;
    }

    public boolean isAccessibleByAuthor() {
        return accessibleByAuthor;
    }

    public void setAccessibleByAuthor(boolean accessibleByAuthor) {
        this.accessibleByAuthor = accessibleByAuthor;
    }

    public boolean isAccessibleByReviewer() {
        return accessibleByReviewer;
    }

    public void setAccessibleByReviewer(boolean accessibleByReviewer) {
        this.accessibleByReviewer = accessibleByReviewer;
    }

    public boolean isAccessibleByQualityAdmin() {
        return accessibleByQualityAdmin;
    }

    public void setAccessibleByQualityAdmin(boolean accessibleByQualityAdmin) {
        this.accessibleByQualityAdmin = accessibleByQualityAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accessibleByFacilitator, accessibleByAuthor, accessibleByReviewer, accessibleByQualityAdmin);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final QuestionStatus other = (QuestionStatus) obj;
        return Objects.equals(this.name, other.name) && Objects.equals(this.accessibleByFacilitator, other.accessibleByFacilitator) && Objects.equals(this.accessibleByAuthor, other.accessibleByAuthor) && Objects.equals(this.accessibleByReviewer, other.accessibleByReviewer) && Objects.equals(this.accessibleByQualityAdmin, other.accessibleByQualityAdmin);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("isStart", isStart)
                .add("isFinish", isFinish)
                .add("accessibleByFacilitator", accessibleByFacilitator)
                .add("accessibleByAuthor", accessibleByAuthor)
                .add("accessibleByReviewer", accessibleByReviewer)
                .add("accessibleByQualityAdmin", accessibleByQualityAdmin)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

}

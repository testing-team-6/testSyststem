package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/20
 * Time: 20:37
 */
@Entity
//TODO business logic to be confirmed
public class ReviewAssessment extends AbstractBaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public ReviewAssessment(String name) {
        this.name = name;
    }

    public ReviewAssessment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

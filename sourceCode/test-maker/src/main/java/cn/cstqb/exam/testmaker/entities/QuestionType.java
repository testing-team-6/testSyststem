package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/30
 * Time: 23:14
 */
@Entity
public class QuestionType extends AbstractBaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public QuestionType(String name) {
        this.name = name;
    }

    public QuestionType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionType that = (QuestionType) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id",id)
                .add("name", name)
                .add("created", createdOn)
                .add("lastModified", updatedOn)
                .toString();
    }


}

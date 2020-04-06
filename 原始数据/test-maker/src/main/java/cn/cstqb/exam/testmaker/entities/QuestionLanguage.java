package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 0:37
 */
@Entity
public class QuestionLanguage extends AbstractBaseEntity {

    @Column(unique = true,nullable = false,length = 55)
    private String name;

    public QuestionLanguage(String name) {
        this.name = name;
    }

    public QuestionLanguage() {
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

        QuestionLanguage that = (QuestionLanguage) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }


}

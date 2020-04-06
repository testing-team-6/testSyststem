package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/29
 * Time: 22:09
 */
@Entity
public class ProjectStatus extends AbstractEntityStatus {
    public ProjectStatus(String name) {
        super(name);
    }

    public ProjectStatus() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectStatus that = (ProjectStatus) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.isStart, that.isStart) &&
                Objects.equal(this.isFinish, that.isFinish) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, isStart, isFinish, createdOn, updatedOn);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("isStart", isStart)
                .add("isFinish", isFinish)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

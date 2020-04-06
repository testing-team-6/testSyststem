package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/3
 * Time: 9:01
 */
@MappedSuperclass
public abstract class AbstractEntityStatus extends AbstractBaseEntity {
    @Column(unique = true,nullable = false)
    protected String name;


    protected boolean isStart;

    protected boolean isFinish;

    protected AbstractEntityStatus(String name) {
        this.name = name;
    }

    protected AbstractEntityStatus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    @JsonIgnore
    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(name);
    }
}

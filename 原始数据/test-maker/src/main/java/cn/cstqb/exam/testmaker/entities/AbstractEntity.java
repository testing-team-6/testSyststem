package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/5
 * Time: 0:45
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    protected Date createdOn;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    protected Date updatedOn;

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @PrePersist
    void onCreate(){
        this.createdOn =new Date();
        this.updatedOn =new Date();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedOn = new Date();
    }

    /**
     * Validate required fields for this entity
     * @return
     */
    public abstract boolean validate();
}

package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.struts2.json.annotations.JSON;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/30
 * Time: 9:37
 */
@MappedSuperclass
abstract class AbstractBaseEntity extends AbstractEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @JSON(serialize = false, deserialize = false)
    @JsonIgnore
    public boolean isValidID() {
        return id != null && id > 0;
    }
}

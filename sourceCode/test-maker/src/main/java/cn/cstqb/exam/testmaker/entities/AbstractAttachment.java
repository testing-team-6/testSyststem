package cn.cstqb.exam.testmaker.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/2
 * Time: 17:01
 */
@MappedSuperclass
abstract class AbstractAttachment extends AbstractBaseEntity {
    @Column(nullable = false, length = 1024)
    protected String path;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

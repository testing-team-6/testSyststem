package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 22:10
 */
@Entity
public class Chapter extends AbstractBaseEntity {

    @Column(nullable = false,length = 20)
    private String number;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "syllabus_id", nullable = false)
    @JsonIgnore
    private Syllabus syllabus;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private List<KnowledgePoint> points=new ArrayList<>();

    public Chapter(int id, String title, Syllabus syllabus) {
        this.id = id;
        this.title = title;
        this.syllabus = syllabus;
    }

    public Chapter(String title, Syllabus syllabus) {
        this.title = title;
        this.syllabus = syllabus;
    }

    public Chapter() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    @JSON(serialize = false)
    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }


    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chapter that = (Chapter) o;

        return Objects.equal(this.number, that.number) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.syllabus, that.syllabus) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, title, syllabus, id, createdOn,
                updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("number", number)
                .add("title", title)
                .add("syllabus", syllabus)
                .add("id", id)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.apache.struts2.json.annotations.JSON;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 22:12
 */
@Entity
public class KnowledgePoint extends AbstractBaseEntity {

    @Column(nullable = false,length = 20)
    private String number;

    @Column(nullable = false,length = 1024)
    private String title;

    @Column(nullable = false,length = 20)
    private String kLevel;

    @Column(nullable = false)
    private short score;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    @JsonIgnore
    private Chapter chapter;

    public KnowledgePoint(int id, String number, String title, String kLevel, Chapter chapter) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.kLevel = kLevel;
        this.chapter = chapter;
    }


    public KnowledgePoint(String number, String title, String kLevel, short score, Chapter chapter) {
        this.number = number;
        this.title = title;
        this.kLevel = kLevel;
        this.chapter = chapter;
        this.score = score;
    }

    public KnowledgePoint() {
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
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

    public String getkLevel() {
        return kLevel;
    }

    public void setkLevel(String kLevel) {
        this.kLevel = kLevel;
    }

//    @JSON(serialize = false)
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnowledgePoint that = (KnowledgePoint) o;

        return Objects.equal(this.number, that.number) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.kLevel, that.kLevel) &&
                Objects.equal(this.score, that.score) &&
                Objects.equal(this.chapter, that.chapter) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, title, kLevel, score, chapter, id,
                createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("number", number)
                .add("title", title)
                .add("kLevel", kLevel)
                .add("score", score)
                .add("chapter", chapter)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }


    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(title) &&
                !Strings.isNullOrEmpty(kLevel)
                && !Strings.isNullOrEmpty(number)
                && score>0;
    }

}

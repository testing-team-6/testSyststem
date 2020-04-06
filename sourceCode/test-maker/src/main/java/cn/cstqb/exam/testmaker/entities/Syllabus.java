package cn.cstqb.exam.testmaker.entities;

import cn.cstqb.exam.testmaker.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 21:37
 */
@Table(
        uniqueConstraints = { @UniqueConstraint(name = "unique_level_version",columnNames = {"level","version"})}
)
@Entity
public class Syllabus extends AbstractBaseEntity {
    @Column(nullable = false)
    private String level;
    @Column(nullable = false)
    private String version;

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isDisabled;

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    public Syllabus(int id, String level, String version) {
        this.id = id;
        this.level = level;
        this.version = version;
    }

    public Syllabus(String level, String version) {
        this.level = level;
        this.version = version;
    }


    public Syllabus() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @JsonIgnore
    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

/*    public List<Chapter> getChapters() {
        return chapters;
    }

    public void addChapter(Chapter chapter) {
        Preconditions.checkNotNull(chapter);
        this.chapters.add(chapter);
    }

    public void removeChapter(Chapter chapter) {
        if (chapter!=null) {
            this.chapters.remove(chapter);
        }
    }*/
/*
    public void setAaData(List<Chapter> chapters) {
        this.chapters = chapters;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Syllabus syllabus = (Syllabus) o;

        if (!id.equals(syllabus.id)) return false;
        if (!level.equals(syllabus.level)) return false;
        if (!version.equals(syllabus.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + level.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("level", level)
                .add("version", version)
                .add("isDisabled", isDisabled)
                .add("createdOn", DateTimeUtils.format(createdOn))
                .add("updatedOn", DateTimeUtils.format(updatedOn))
                .toString();
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(level) && !Strings.isNullOrEmpty(version);
    }
}

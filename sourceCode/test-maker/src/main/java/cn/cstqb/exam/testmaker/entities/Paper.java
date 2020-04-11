package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//该类参照Project和Question建成
//因为继承了AbstractBaseEntity类和AbstractEntity类故不需id和created属性
@Entity
public class Paper extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    //column注解用来标识实体类中属性与数据表中字段的对应关系。
    @Column(nullable = false)
    private String name;

    @OneToMany
    @JsonIgnore
    private List<Question> questions;
    //放试卷的题目

    public Paper(Project project,String name){
        this.project = project;
        this.name = name;
    }

    public Paper() {

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        if (!questions.contains(question)) {
            questions.add(question);
        }
    }

    public void deleteQuestion(Question question){
        questions.remove(question);
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id",id)
                .add("name",name)
                .add("project", project)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( project,name,id,createdOn,updatedOn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Paper other = (Paper) obj;
        return java.util.Objects.equals(this.name, other.name)&&
                java.util.Objects.equals(this.project, other.project)&&
                java.util.Objects.equals(this.id, other.id)&&
                java.util.Objects.equals(this.createdOn, other.createdOn)&&
                java.util.Objects.equals(this.updatedOn, other.updatedOn)
                ;
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return project != null
                && name != null
                ;
    }
    public boolean validateBasicFields(){
        return  name != null;
    }
}

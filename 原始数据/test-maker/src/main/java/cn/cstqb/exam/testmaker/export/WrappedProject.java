package cn.cstqb.exam.testmaker.export;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/8
 * Time: 19:38
 */
public class WrappedProject {
    private Project project;
    private Collection<Question> questions;

    public WrappedProject(Project project, Collection<Question> questions) {
        this.project = project;
        this.questions = questions;
    }

    public WrappedProject(Project project) {
        this.project = project;
    }

    public WrappedProject() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }
}

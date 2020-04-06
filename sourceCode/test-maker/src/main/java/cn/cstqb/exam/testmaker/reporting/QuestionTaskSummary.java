package cn.cstqb.exam.testmaker.reporting;

import cn.cstqb.exam.testmaker.entities.Project;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 20:35
 */
public class QuestionTaskSummary {
    private String username;
    private Project project;
    private Set<QuestionTaskEntry> entries;

    public QuestionTaskSummary(String username, Project project, Set<QuestionTaskEntry> entries) {
        this.username = username;
        this.project = project;
        this.entries = entries;
    }

    public QuestionTaskSummary() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<QuestionTaskEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<QuestionTaskEntry> entries) {
        this.entries = entries;
    }
}

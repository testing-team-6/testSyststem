package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/24
 * Time: 8:38
 */
//@Entity
public class ProjectUser extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "PROJECT_USER_ID"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Collection<Role> roles;

    public ProjectUser(Project project, User user) {
        this.project = project;
        this.user = user;
    }

    public ProjectUser() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectUser that = (ProjectUser) o;

        return Objects.equal(this.project, that.project) &&
                Objects.equal(this.user, that.user) &&
                Objects.equal(this.roles, that.roles) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(project, user, roles, id, createdOn, updatedOn);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("project", project)
                .add("user", user)
                .add("roles", roles)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

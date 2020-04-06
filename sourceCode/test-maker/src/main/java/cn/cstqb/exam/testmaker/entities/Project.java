package cn.cstqb.exam.testmaker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 17:18
 */
@Entity
public class Project extends AbstractBaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "FACILITATOR_ID", nullable = false)
    private User facilitator;

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date finishDate;

    @OneToOne
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private ProjectStatus status;

    @OneToOne
    @JoinColumn(name = "SYLLABUS_ID")
//    @JsonIgnore
    private Syllabus syllabus;

    @ManyToMany
    @JoinTable(
            joinColumns={@JoinColumn(name="PROJECT_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")}
    )
    @JsonIgnore
    private List<User> users;

    @JsonIgnore
    private String customField1;
    @JsonIgnore
    private String customField2;
    @JsonIgnore
    private String customField3;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exportedOn;

    @OneToOne
    private User exportedBy;

    public Project(String name, User facilitator, ProjectStatus status) {
        this.name = name;
        this.facilitator = facilitator;
        this.status = status;
    }

    public Project(String name) {
        this.name = name;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(User facilitator) {
        this.facilitator = facilitator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }

    public String getCustomField1() {
        return customField1;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    public Date getExportedOn() {
        return exportedOn;
    }

    public void setExportedOn(Date exportedOn) {
        this.exportedOn = exportedOn;
    }

    public User getExportedBy() {
        return exportedBy;
    }

    public void setExportedBy(User exportedBy) {
        this.exportedBy = exportedBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, facilitator, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        return Objects.equals(this.name, other.name) && Objects.equals(this.facilitator, other.facilitator) && Objects.equals(this.status, other.status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("facilitator", facilitator != null ? facilitator.getUsername() : null)
                .add("syllabus", syllabus != null ? String.format("%s (%s)", syllabus.getLevel(), syllabus.getVersion()) : null)
                .add("startDate", startDate)
                .add("finishDate", finishDate)
                .add("status", status)
                .toString();
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(name) && facilitator!=null;
    }
}

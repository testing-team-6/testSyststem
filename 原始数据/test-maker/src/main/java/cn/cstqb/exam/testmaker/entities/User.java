package cn.cstqb.exam.testmaker.entities;

import cn.cstqb.exam.testmaker.util.HashUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.apache.struts2.json.annotations.JSON;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/26
 * Time: 19:40
 */
@Entity
@NamedQuery(name = "User.findByUserName", query = "SELECT u from User u where u.username = :username")
public class User extends AbstractBaseEntity {
    @Column(unique = true, nullable = false, updatable = false, length = 40)
    private String username;

    @Column(nullable = false, length = 40)
    @JsonIgnore
    private String fullName;

    @Column(unique = true, nullable = false)
    @JsonIgnore
    private String email;

    @JsonIgnore
    private String phone;

    @Column(nullable = false, length = 64,columnDefinition = "CHAR(64)")
    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled=true;

    @JsonIgnore
    private int loginCount;

    @JsonIgnore
    private boolean isAdmin;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date lastLogin;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Project> projects;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JSON(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JSON(serialize = false)
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @JSON(serialize = false, deserialize = false)
    public List<Project> getProjects() {
        return projects;
    }

    @JSON(deserialize = false)
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, phone, password, enabled, loginCount, isAdmin, lastLogin, fullName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username)
                && Objects.equals(this.email, other.email)
                && Objects.equals(this.phone, other.phone)
                && Objects.equals(this.password, other.password)
//                && Objects.equals(this.enabled, other.enabled)
//                && Objects.equals(this.loginCount, other.loginCount)
//                && Objects.equals(this.isAdmin, other.isAdmin)
//                && Objects.equals(this.lastLogin, other.lastLogin)
                && Objects.equals(this.fullName, other.fullName)
                ;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("username", username)
                .add("email", email)
                .add("phone", phone)
//                .add("password", password)
                .add("enabled", enabled)
                .add("loginCount", loginCount)
                .add("isAdmin", isAdmin)
                .add("lastLogin", lastLogin)
                .add("fullName", fullName)
                .toString();
    }


    @PrePersist
    @PreUpdate
    private void hashPassword() {
        if (this.password!=null && this.password.length() < 64) {
            this.password = HashUtil.hash(password);
        }
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(username)
//                && !Strings.isNullOrEmpty(password)
                && !Strings.isNullOrEmpty(email)
                && !Strings.isNullOrEmpty(fullName);
    }
}

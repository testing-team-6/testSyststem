package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/18
 * Time: 21:25
 */
public interface IProjectService {

    /**
     * Finds project by name
     * @param projectName
     * @return
     */
    Project find(String projectName);

    /**
     *
     * @param id
     * @return
     */
    Project find(int id);

    Project saveOrUpdate(Project project);

    /**
     * Creates or updates a project status
     * @param status
     */
    void saveOrUpdate(ProjectStatus status);

    /**
     * Find projects with the given facilitator
     * @param user
     * @return
     */
    List<Project> findByFacilitator(User user);

    List<User> findProjectUsers(Project project, boolean activeOnly);

    List<User> findProjectUsers(String projectName, boolean activeOnly);

    /**
     * Finds all projects the user is associated with. The user can be in the role of a facilitator, reviewer, author or quality admin.
     * @param user
     * @return
     */
    List<Project> findProjects(User user);

    /**
     * Finds all projects in the database with optional flag to retrieve active project only
     * @param activeProjectsOnly flag to load active projects only
     * @return all projects
     */
    List<Project> findProjects(boolean activeProjectsOnly);

    /**
     * Finds all projects for this use.
     * @param user
     * @param activeProjectsOnly <b>True</b> to retrieve active projects only
     * @return
     */
    List<Project> findProjects(User user, boolean activeProjectsOnly);


    /**
     * Find projects with the given status
     * @param status
     * @return
     */
    List<Project> findByStatus(ProjectStatus status);

    Project findFirst();

    boolean exists(Project project);
}

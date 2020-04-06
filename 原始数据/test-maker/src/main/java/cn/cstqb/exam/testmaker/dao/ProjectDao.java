package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 18:13
 */
public interface ProjectDao extends GenericDao<Project, Integer> {
    /**
     * Find projects with the given facilitator
     *
     * @param user
     * @return The project objects matching the facilitator or <i>null</i> if
     * not found
     */
    List<Project> findByFacilitator(User user);

    /**
     * Find projects with the given status
     *
     * @param projectStatus
     * @return The project objects matching the status or <i>null</i> if not
     * found
     */
    List<Project> findByStatus(ProjectStatus projectStatus);

    /**
     * Finds project by name
     *
     * @param projectName
     * @return
     */
    Project find(String projectName);

    /**
     * Find not finished projects
     *
     * @return
     */
    List<Project> findActive();

}

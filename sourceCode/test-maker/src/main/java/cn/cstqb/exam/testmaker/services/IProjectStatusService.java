package cn.cstqb.exam.testmaker.services;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.ProjectStatus;

public interface IProjectStatusService {
	/**
	 * Create or update a project status
	 * @param projectStatus
	 */
	void saveOrUpdate(ProjectStatus projectStatus);

	/**
	 * Delete the specific project status
	 * @param projectStatus
	 */
	void delete(ProjectStatus projectStatus);

	/**
	 * Find all project status
	 * @return all project status objects
	 */
	List<ProjectStatus> findAll();

	/**
	 * get the project status object according to the project status name
	 * @return the project status matching the given name
	 */
	ProjectStatus findByName(String name);

    /**
     *
     * @param id
     * @return
     */
	ProjectStatus findById(Integer id);

    ProjectStatus findStartState();

    /**
     * Test if it's OK to add a status as start status because there should be only one start status in the system.
     * @return <b>True</b> if there's no start status yet.
     */
    boolean canAddStartStatus();
}

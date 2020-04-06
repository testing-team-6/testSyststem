package cn.cstqb.exam.testmaker.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.*;
import cn.cstqb.exam.testmaker.dao.ProjectStatusDao;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.services.IProjectStatusService;


public class ProjectStatusServiceImpl implements IProjectStatusService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ProjectStatusDao projectStatusDao;

	@Inject
	public ProjectStatusServiceImpl(ProjectStatusDao projectStatusDao) {
		this.projectStatusDao = projectStatusDao;
	}

	/**
	 * Create or update a project status
	 * @param projectStatus
	 */
	@Override
	public void saveOrUpdate(ProjectStatus projectStatus) {
		checkArgument(projectStatus != null, "Invalid project status");
		checkArgument(!Strings.isNullOrEmpty(projectStatus.getName()), "the name of project status cannot be null or empty");
		ProjectStatus persisted = projectStatusDao.findById(projectStatus.getId());

        /*
         * try to search by name if id is not available since name is unique for project status
         */
        if (persisted == null) {
            persisted = projectStatusDao.findByName(projectStatus.getName());
        }

        /*
         * If persisted is still null, this indicates the incoming status is a new one; otherwise, it should be an existing one.
         */
        if (persisted == null) {
			projectStatusDao.create(projectStatus);
        } else {
            persisted.setName(projectStatus.getName());
            persisted.setStart(projectStatus.isStart());
            persisted.setFinish(projectStatus.isFinish());
            logger.info("ProjectStatusServiceImpl.saveOrUpdate: Merged object: {}", persisted );
            projectStatusDao.update(persisted);
        }
    }

	/**
	 * Delete the specific project status
	 * @param projectStatus
	 */
	@Override
	public void delete(ProjectStatus projectStatus) {
		checkArgument(projectStatus != null, "Invalid project status");
        checkState(projectStatus.validate() && projectStatus.isValidID(), "The project status name and id cannot be null. Given: %s", projectStatus);
        ProjectStatus persisted = projectStatusDao.findById(projectStatus.getId());
        logger.debug("ProjectStatusServiceImpl.delete: About to delete ProjectStatus: {}", persisted );
        projectStatusDao.delete(persisted);
	}

	/**
	 * Find all project status
	 * @return all project status objects
	 */
	@Override
	public List<ProjectStatus> findAll() {
		return projectStatusDao.findAll();
	}

    /**
     * @param id
     * @return
     */
    @Override
    public ProjectStatus findById(Integer id) {
        if (id==null || id<1) {
            logger.error("ProjectStatusServiceImpl.findById: he object ID must not be null or less than 1: {}", id );
            return null;
        }
        return projectStatusDao.findById(id);
    }

    /**
	 * get the project status object according to the project status name
	 * @return the project status matching the given name
	 */
	@Override
	public ProjectStatus findByName(String name) {
		return projectStatusDao.findByName(name);
	}

    @Override
    public ProjectStatus findStartState() {
        return projectStatusDao.findStart();
    }

    @Override
    public boolean canAddStartStatus() {
        return projectStatusDao.canAddStartStatus();
    }
}

package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.ProjectDao;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IProjectStatusService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.DateFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class ProjectServiceImpl implements IProjectService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ProjectDao projectDao;
    private IUserService userService;
    @Inject
    private IProjectStatusService statusService;

    @Inject
    public ProjectServiceImpl(ProjectDao projectDao, IUserService userService) {
        this.projectDao = projectDao;
        this.userService = userService;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Project find(int id) {
        checkArgument(id>0,"The project id must be greater than zero. %s", id);
        return projectDao.findById(id);
    }

    /**
     * Finds project by name
     *
     * @param projectName
     * @return
     */
    @Override
    public Project find(String projectName) {
        return projectDao.find(projectName);
    }

    @Override
	public Project saveOrUpdate(Project project) {
		checkArgument(project != null, "the project can not be null or empty");
        Integer id = project.getId();
		if (id == null || id<1) {
			projectDao.create(project);
		} else {
			return projectDao.update(project);
		}
        return project;
    }

	@Override
	public void saveOrUpdate(ProjectStatus projectStatus) {
//		checkArgument(projectStatus != null && projectStatus.getId() > 0,
//				"Invalid project status data. id : %s", projectStatus.getId());
//		Project persisted = Project.findById(projectStatus
//				.getId());
//		if (persisted == null) {
//			project.create(projectStatus);
//		} else {
//			project.update(projectStatus);
//		}
	}

    @Override
    public List<User> findProjectUsers(Project project, boolean activeOnly) {
        Preconditions.checkNotNull(project);
        checkArgument(project.validate() && project.isValidID());
        return findProjectUsers(project.getName(), activeOnly);
    }

    @Override
    public List<User> findProjectUsers(String projectName, boolean activeOnly) {
        checkArgument(!Strings.isNullOrEmpty(projectName));
        Project project = projectDao.find(projectName);
        if (project == null) {
            return null;
        }
        if (activeOnly) {
            Collection<User> filtered= Collections2.filter(project.getUsers(),new Predicate<User>() {
                @Override
                public boolean apply(@Nullable User input) {
                    if (input == null) {
                        return false;
                    }
                    return input.isEnabled();
                }
            });
            return new ArrayList<>(filtered);
        }
        return project.getUsers();
    }

    @Override
	public List<Project> findByFacilitator(User user) {
		return projectDao.findByFacilitator(user);
	}

	@Override
	public List<Project> findByStatus(ProjectStatus projectStatus) {
		return projectDao.findByStatus(projectStatus);
	}

    /**
     * Finds all projects the user is associated with. The user can be in the role of a facilitator, reviewer, author or quality admin.
     *
     * @param user
     * @return
     */
    @Override
    public List<Project> findProjects(User user) {
        return findProjects(user,false);
    }

    /**
     * Finds all projects for this use.
     *
     * @param user
     * @param activeProjectsOnly <b>True</b> to retrieve active projects only
     * @return
     */
    @Override
    public List<Project> findProjects(User user, boolean activeProjectsOnly) {
        checkArgument(user!=null && user.validate());
        //checkState(user.getProjects()!=null && !user.getProjects().isEmpty(),"The user is not associated with any project: %s", user);
        List<Project> projects=user.getProjects();
        List<Project> filtered = new ArrayList<>(projects);
        if (activeProjectsOnly) {
            for (Iterator<Project> iterator = filtered.iterator(); iterator.hasNext(); ) {
                Project project = iterator.next();
                if (project.getStatus().isFinish()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("The project has ended. Excluded from results. {}", project.getName());
                    }
                    iterator.remove();
                }
            }
        }
        return filtered;
    }

    /**
     * Finds all projects in the database with optional flag to retrieve active project only
     *
     * @param activeProjectsOnly flag to load active projects only
     * @return all projects
     */
    @Override
    public List<Project> findProjects(boolean activeProjectsOnly) {
        if (activeProjectsOnly) {
            return projectDao.findActive();
        }else {
            return projectDao.findAll();
        }
    }

    @Override
    public Project findFirst() {
        String projectName = "TEST-FIRST-PROJECT";
        Project first = projectDao.find(projectName);
        if (first == null) {
            Calendar cal=Calendar.getInstance();
            User user = userService.findFirstUser();
            ProjectStatus status = statusService.findStartState();
            if (status == null) {
                status = new ProjectStatus("INITIAL");
                status.setStart(true);
                statusService.saveOrUpdate(status);
            }
            first = new Project(projectName, user, status);
            first.setStartDate(new Date());
            cal.add(Calendar.MONTH,3);
            first.setFinishDate(cal.getTime());
            projectDao.create(first);
        }
        return first;
    }

    @Override
    public boolean exists(Project project) {
        Preconditions.checkArgument(project != null);
        Project persisted = null;
        logger.debug("ProjectServiceImpl.exists: project:{}", project);
        if (project.isValidID()){
            persisted = projectDao.findById(project.getId());
        } else if (!Strings.isNullOrEmpty(project.getName())) {
            persisted = projectDao.findSingleResult("name", project.getName());
        }
        return persisted != null;
    }
}

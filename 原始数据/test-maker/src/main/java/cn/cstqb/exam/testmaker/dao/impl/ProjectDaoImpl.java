package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.ProjectDao;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Jian-Min Gao Date: 2014/12/27 Time: 18:13
 */
public class ProjectDaoImpl extends GenericJpaDaoImpl<Project, Integer>
		implements ProjectDao {
    /**
     * Finds project by name
     *
     * @param projectName
     * @return
     */
    @Override
    public Project find(String projectName) {
        return findSingleResult("name", projectName);
    }

    /**
	 * Find projects with the given facilitator
	 *
	 * @param user
	 * @return The project objects matching the facilitator or <i>null</i> if
	 *         not found
	 */
	@Override
	public List<Project> findByFacilitator(User user) {
		return findResultList("facilitator", user);
	}

	/**
	 * Find projects with the given status
	 *
	 * @param projectStatus
	 * @return The project objects matching the status or <i>null</i> if not
	 *         found
	 */
	@Override
	public List<Project> findByStatus(ProjectStatus projectStatus) {
		return findResultList("status", projectStatus);
	}

    /**
     * Find not finished projects
     *
     * @return
     */
    @Override
    @Transactional
    public List<Project> findActive() {
        EntityManager em = provider.get();
        return em.createQuery("SELECT p FROM Project p WHERE p.status.isFinish = false ORDER BY p.name ASC", Project.class)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        EntityManager em = provider.get();
        return em.createQuery("SELECT p FROM Project p ORDER BY p.name ASC", Project.class).getResultList();
    }
}

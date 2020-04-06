package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.ProjectStatusDao;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import com.google.inject.persist.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/29
 * Time: 22:14
 */
public class ProjectStatusDaoImpl extends GenericJpaDaoImpl<ProjectStatus, Integer> implements ProjectStatusDao {
    /**
     * Find the project status with the given name
     *
     * @param statusName
     * @return
     */
    @Override
    public ProjectStatus findByName(String statusName) {
        return findSingleResult("name", statusName);
    }

    /**
     * Check if it's OK to add new Start status. There should be only 1 start
     *
     * @return <b>True</b> if it's OK to add a Start status; otherwise false.
     */
    @Override
    public boolean canAddStartStatus() {
        List<ProjectStatus> statuses = findResultList("isStart", true);
        return statuses == null || statuses.isEmpty();
    }

    @Override
    public ProjectStatus findStart() {
        List<ProjectStatus> statuses = findResultList("isStart", true);
        if(statuses == null || statuses.isEmpty()) return null;
        if (logger.isWarnEnabled()) {
            logger.warn("More than one START statuses found: {}", statuses);
        }
        return statuses.get(0);
    }


}

package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.ProjectStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/29
 * Time: 22:13
 */
public interface ProjectStatusDao extends GenericDao<ProjectStatus, Integer> {

    /**
     * Find the project status with the given name
     *
     * @param statusName
     * @return
     */
    ProjectStatus findByName(String statusName);


    /**
     * Check if it's OK to create a new START status. There should be only 1 START status in the db table.
     *
     * @return <b>True</b> if there's no such one yet; otherwise false.
     */
    boolean canAddStartStatus();

    ProjectStatus findStart();
}

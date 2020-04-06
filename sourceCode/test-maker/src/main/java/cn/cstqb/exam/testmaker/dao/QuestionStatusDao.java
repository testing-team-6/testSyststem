package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 16:53
 */
public interface QuestionStatusDao extends GenericDao<QuestionStatus, Integer> {
    /**
     * Find status by name
     * @param statusName
     * @return
     */
    QuestionStatus findByName(String statusName);

    /**
     * Check if it's OK to create a new START status. There should be only 1 START status in the db table.
     * @return <b>True</b> if there's no such one yet; otherwise false.
     */
    boolean canAddStartStatus();

    /**
     * Finds start status
     * @return
     */
    QuestionStatus findStart();
}

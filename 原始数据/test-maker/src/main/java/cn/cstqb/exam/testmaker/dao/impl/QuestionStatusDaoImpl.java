package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.QuestionStatusDao;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.QuestionStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 16:54
 */
public class QuestionStatusDaoImpl extends GenericJpaDaoImpl<QuestionStatus, Integer> implements QuestionStatusDao {
    /**
     * Find status by name
     *
     * @param statusName
     * @return
     */
    @Override
    public QuestionStatus findByName(String statusName) {
        return findSingleResult("name", statusName);
    }

    /**
     * Check if it's OK to create a new START status. There should be only 1 START status in the db table.
     *
     * @return <b>True</b> if there's no such one yet; otherwise false.
     */
    @Override
    public boolean canAddStartStatus() {
        List<QuestionStatus> statuses = findResultList("isStart", true);
        return statuses == null || statuses.isEmpty();
    }

    /**
     * Finds start status
     *
     * @return
     */
    @Override
    public QuestionStatus findStart() {
        List<QuestionStatus> statuses = findResultList("isStart", true);
        if(statuses == null || statuses.isEmpty()) return null;
        if (logger.isWarnEnabled()) {
            logger.warn("More than one START statuses found: {}", statuses);
        }
        return statuses.get(0);
    }

/*    @Override
    public List<QuestionStatus> findAll() {
        EntityManager em=provider.get();
        return em.createQuery("SELECT s FROM QuestionStatus s ORDER BY s.name", QuestionStatus.class)
                .getResultList();
    }*/
}

package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.QuestionReportingDao;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.reporting.QuestionTaskEntry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 20:32
 */
public class QuestionReportingDaoImpl extends QuestionDaoImpl implements QuestionReportingDao {

    private Calendar calendar;

    public QuestionReportingDaoImpl() {
        calendar = Calendar.getInstance();
    }

    /**
     * Get the questions which are within the warning threshold
     *
     * @param project
     * @param username
     * @param threshold
     * @return
     */
    @Override
    public List<Question> getExpiringQuestions(Project project, String username, int threshold) {
        EntityManager em = provider.get();
        calendar.add(Calendar.DAY_OF_MONTH, threshold);
        Date thresholdDate = calendar.getTime();
        logger.debug("QuestionReportingDaoImpl.getExpiringQuestions: threshold date: {}", thresholdDate );
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q JOIN q.reviewers as r WHERE q.project =:project " +
                "AND(  ( q.author.username =:username AND q.authoringFinishDate <=:thresholdDate)" +
                "OR ( r.username =:username AND q.reviewingFinishDate <=:thresholdDate)" +
                ")",
                    Question.class)
                .setParameter("project", project)
                .setParameter("username",username)
                .setParameter("thresholdDate",thresholdDate)
                ;
        return query.getResultList();
    }

    public List<Question> getExpiredQuestions(Project project, String username) {
        EntityManager em = provider.get();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date thresholdDate = calendar.getTime();
        logger.debug("QuestionReportintDaoImpl.getExpiredQuestions: threshold date: {}", thresholdDate);
        TypedQuery<Question> query = em.createQuery("SELECT q from Question q JOIN q.reviewers as r WHERE q.project = :project " +
                "AND(  ( q.author.username =:username AND q.authoringFinishDate =:thresholdDate)" +
                "OR ( r.username =:username AND q.reviewingFinishDate =:thresholdDate)" +
                ")",
                    Question.class)
                .setParameter("project", project)
                .setParameter("username", username)
                .setParameter("thresholdDate", thresholdDate)
                ;
        return query.getResultList();
    }

    /**
     * @param project
     * @param threshold
     * @return
     */
    @Override
    public List<Question> getExpiringQuestions(Project project, int threshold) {
        EntityManager em = provider.get();
        calendar.add(Calendar.DAY_OF_MONTH, threshold);
        Date thresholdDate = calendar.getTime();
        logger.debug("QuestionReportingDaoImpl.getExpiringQuestions: threshold date: {}", thresholdDate );
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q JOIN q.reviewers as r WHERE q.project =:project " +
                        "AND (  q.authoringFinishDate <=:thresholdDate OR q.reviewingFinishDate <=:thresholdDate)",
                Question.class)
                .setParameter("project", project)
                .setParameter("thresholdDate",thresholdDate);
        return query.getResultList();

    }

    /**
     * Finds the question statistics for the project and user
     *
     * @param project
     * @param username
     * @return
     */
    @Override
    public Set<QuestionTaskEntry> getTasks(Project project, String username) {
        Set<QuestionTaskEntry> tasks = new TreeSet<>(new Comparator<QuestionTaskEntry>() {
            @Override
            public int compare(QuestionTaskEntry o1, QuestionTaskEntry o2) {
                return o1.getType().name().compareTo(o2.getType().name());
            }
        });
        tasks.add(getAuthorTask(project, username));
        tasks.add(getReviewerTask(project, username));
        tasks.add(getQATask(project, username));
        return tasks;
    }

    @Override
    public QuestionTaskEntry getProjectSummary(Project project) {
        EntityManager em = provider.get();
        Long total=0l;

        try {
            total = em.createQuery("SELECT count(DISTINCT q) FROM Question q WHERE q.project =:project ",
                    Long.class)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getProjectSummary: {}",e );
        }

        Long pending=0l;
        try {
            pending = em.createQuery("SELECT count(DISTINCT q) FROM Question q WHERE q.project =:project " +
                            "AND q.status.isFinish = FALSE ",
                    Long.class)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getProjectSummary: {}",e );
        }

        QuestionTaskEntry entry = new QuestionTaskEntry(QuestionTaskEntry.EntryType.ALL, total, pending);

        logger.info("QuestionStatisticsDaoImpl.getProjectSummary: task summary: {}",  entry );
        return entry;
    }

    @Override
    public QuestionTaskEntry getTaskSummary(Project project, String username) {
        EntityManager em = provider.get();
        Long total=0l;

        try {
            total = em.createQuery("SELECT count(DISTINCT q) FROM Question q JOIN q.reviewers as r WHERE q.project =:project " +
                            "AND ( q.author.username =:user OR r.username =:user OR q.qualityAdmin.username =:user)",
                    Long.class)
                    .setParameter("user", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getTaskSummary: {}",e );
        }

        Long pending=0l;
        try {
            pending = em.createQuery("SELECT count(DISTINCT q) FROM Question q JOIN q.reviewers as r WHERE q.project =:project " +
                            "AND q.status.isFinish = FALSE " +
                            "AND ( q.author.username =:user OR r.username =:user OR q.qualityAdmin.username =:user)",
                    Long.class)
                    .setParameter("user", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getTaskSummary: {}",e );
        }

        QuestionTaskEntry entry = new QuestionTaskEntry(QuestionTaskEntry.EntryType.ALL, total, pending);

        logger.info("QuestionStatisticsDaoImpl.getAuthorTask: task summary for {}: {}", username, entry );
        return entry;
    }

    public QuestionTaskEntry getAuthorTask(Project project, String username) {
        EntityManager em = provider.get();
        Long total=0l;

        try {
            total = em.createQuery("SELECT distinct count(q) FROM Question q WHERE q.author.username =:author AND q.project =:project", Long.class)
                    .setParameter("author", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getAuthorTask: {}",e );
        }

        logger.info("QuestionStatisticsDaoImpl.getAuthorTask: {} questions in total for {}", total, username);


        Long pending=0l;
        try {
            pending = em.createQuery("SELECT count(q) FROM Question q WHERE q.author.username =:author AND q.project =:project AND q.status.accessibleByAuthor = true", Long.class)
                    .setParameter("author", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getAuthorTask: {}",e );
        }

        QuestionTaskEntry entry = new QuestionTaskEntry(QuestionTaskEntry.EntryType.AUTHOR, total, pending);

        logger.info("QuestionStatisticsDaoImpl.getAuthorTask: task summary for {}: {}", username, entry );
        return entry;
    }

    @Override
    public QuestionTaskEntry getReviewerTask(Project project, String username) {
        EntityManager em = provider.get();
        Long total=0l;

        try {
            total = em.createQuery("SELECT count(q) FROM Question q JOIN q.reviewers as r " +
                    "WHERE r.username =:username AND q.project =:project", Long.class)
                    .setParameter("username", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getReviewerTask: {}",e );
        }

        logger.info("QuestionStatisticsDaoImpl.getReviewerTask: {} questions in total for {}", total, username);


        Long pending=0l;
        try {
            pending = em.createQuery("SELECT count(q) FROM Question q JOIN q.reviewers as r " +
                    "WHERE r.username =:username AND q.project =:project AND q.status.accessibleByReviewer = TRUE", Long.class)
                    .setParameter("username", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getReviewerTask: {}",e );
        }

        QuestionTaskEntry entry = new QuestionTaskEntry(QuestionTaskEntry.EntryType.REVIEWER, total, pending);

        logger.info("QuestionStatisticsDaoImpl.getReviewerTask: task summary for {}: {}", username, entry );
        return entry;
    }

    @Override
    public QuestionTaskEntry getQATask(Project project, String username) {
        EntityManager em = provider.get();
        Long total=0l;

        try {
            total = em.createQuery("SELECT count(q) FROM Question q WHERE q.qualityAdmin.username =:username AND q.project =:project", Long.class)
                    .setParameter("username", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getQATask: {}",e );
        }

        logger.info("QuestionStatisticsDaoImpl.getQATask: {} questions in total for {}", total, username);


        Long pending=0l;
        try {
            pending = em.createQuery("SELECT count(q) FROM Question q WHERE q.qualityAdmin.username =:username AND q.project =:project AND q.status.accessibleByQualityAdmin = TRUE ", Long.class)
                    .setParameter("username", username)
                    .setParameter("project", project)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("QuestionStatisticsDaoImpl.getQATask: {}",e );
        }

        QuestionTaskEntry entry = new QuestionTaskEntry(QuestionTaskEntry.EntryType.QA, total, pending);

        logger.info("QuestionStatisticsDaoImpl.getQATask: task summary for {}: {}", username, entry );
        return entry;
    }
}

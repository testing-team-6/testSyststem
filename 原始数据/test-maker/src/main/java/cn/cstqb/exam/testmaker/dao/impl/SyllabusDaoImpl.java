package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.SyllabusDao;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:08
 */
public class SyllabusDaoImpl extends GenericJpaDaoImpl<Syllabus, Integer> implements SyllabusDao {

	/**
	 * find the syllabus with the given level
	 * @param syllabus
	 * @return The syllabus object matching the level or <i>null</i> if not
	 *         found
	 */
    @Transactional
	@Override
	public Syllabus findSyllabus(Syllabus syllabus) {
        Syllabus persisted=null;
        Integer id = syllabus.getId();
        if (id != null && id > 0) {
            persisted = findById(id);
        } else if(!Strings.isNullOrEmpty(syllabus.getLevel()) && !Strings.isNullOrEmpty(syllabus.getVersion())) {
            EntityManager em = provider.get();
            try {
                persisted = em.createQuery("SELECT s FROM Syllabus s WHERE s.level=:level and s.version=:version", Syllabus.class)
                        .setParameter("level", syllabus.getLevel())
                        .setParameter("version", syllabus.getVersion())
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
        return persisted;
	}

    @Override
    @Transactional
    public List<Syllabus> findAll() {
        EntityManager em = provider.get();
        return em.createQuery("SELECT s FROM Syllabus s ORDER BY s.level ASC", Syllabus.class).getResultList();
    }

    /**
     * Retrieve syllabuses from the server depending on the given the flag to include disabled syllabuses or not
     *
     * @param includeDisabled true to get all syllabuses; <b>false</b> to include only active ones
     * @return a list of syllabuses
     */
    @Override
    public List<Syllabus> findSyllabuses(boolean includeDisabled) {
        logger.debug("SyllabusDaoImpl.findSyllabuses: includeDisabled: {}",includeDisabled);
        if (includeDisabled) return findAll();

        EntityManager em = provider.get();
        return em.createQuery("SELECT s FROM Syllabus s WHERE s.isDisabled = FALSE ORDER BY s.level ASC", Syllabus.class)
                .getResultList();
    }

    @Override
    public List<Project> findSyllabuses(Syllabus syllabus) {
        logger.debug("SyllabusDaoImpl.findSyllabuses: Finding project using syllabus [{}]", syllabus.getId() );
        EntityManager em = provider.get();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> root = cq.from(Project.class);
        cq.select(root);
        cq.where(cb.equal(root.get("syllabus"),syllabus));

        TypedQuery<Project> query = em.createQuery(cq);
        List<Project> projects=query.getResultList();
        if (projects!=null && logger.isDebugEnabled()) {
            StringBuilder text = new StringBuilder();
            for (Project project : projects) {
                text.append(project.getName()).append(", ");
            }
            logger.debug("SyllabusDaoImpl.findSyllabuses: Syllabus [{}] is being used by projects: [{}]",syllabus.getId(), text );
        }
        return projects;
    }
}

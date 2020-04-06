package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.DaoUtil;
import cn.cstqb.exam.testmaker.dao.GenericDao;
import cn.cstqb.exam.testmaker.entities.AbstractEntity;
import com.google.common.base.CaseFormat;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 22:45
 */
public class GenericJpaDaoImpl<T extends AbstractEntity, ID extends Serializable> implements GenericDao<T, ID> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Class<T> klass;
    protected String tableName;
    @Inject protected Provider<EntityManager> provider;

    @Inject
    GenericJpaDaoImpl() {
        klass=(Class<T>) DaoUtil.getTypeArguments(GenericJpaDaoImpl.class, this.getClass()).get(0);
        if (logger.isDebugEnabled()) {
            logger.debug("Parameter type: {}", klass.getName());
        }
        tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, klass.getSimpleName());
    }


    @Transactional
    @Override
    public void create(T entity) {
        Objects.requireNonNull(entity, "The entity must not be null.");
        if (logger.isDebugEnabled()) {
            logger.debug("GenericDao: Saving {} entity: {}", entity.getClass().getSimpleName(), entity);
        }
        EntityManager em=provider.get();
        em.persist(entity);
        em.flush();
    }

    @Transactional
    @Override
    public void delete(T entity) {
        Objects.requireNonNull(entity, "The entity must not be null!");
        EntityManager em=provider.get();
        em.remove(entity);
    }

    @Transactional
    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity, "The entity must not be null!");
        EntityManager em=provider.get();
        return em.merge(entity);
    }

//    @Transactional
    @Override
    public T findById(ID id) {
        if(id==null) return null;
        if (logger.isDebugEnabled()) {
            logger.debug("Id type: {}, value: {}", id.getClass().getName(), id);
            logger.debug("{}: Finding entity [{}] with id #{}", Thread.currentThread().getStackTrace()[1].getMethodName(),klass.getSimpleName(), id);
        }
        EntityManager em=provider.get();
        return em.find(klass, id);
    }

//    @Transactional
    @Override
    public List<T> findAll() {
        EntityManager em=provider.get();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(klass);
        Root<T> root = cq.from(klass);
        cq.select(root);

        TypedQuery<T> q = em.createQuery(cq);
//        em.flush();//Fix display issue in syllabus table
        return q.getResultList();
    }

    public boolean checkExistsByIdOrUniqueProperty(ID id, String prop, Object value) {
        if(id==null && value==null) return false;
        T persisted=null;
        if (id != null) {
            persisted = findById(id);
        } else {
            persisted = findSingleResult(prop, value);
        }
        logger.debug("GenericJpaDaoImpl.checkExistsByIdOrUniqueProperty: Persisted entity found: {}", persisted );
        return persisted != null;
    }

    @Override
    public List<T> findResultList(String param, Object value) {
        return queryList(buildQuery(param, value));
    }

    /**
     * paginated query
     *
     * @param param
     * @param value
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public List<T> findResultList(String param, Object value, int pageSize, int pageNumber) {
        return queryListPaginated(buildQuery(param, value), pageSize, pageNumber);
    }

    public T findSingleResult(String param, Object value) {
        TypedQuery<T> query = buildQuery(param, value);
        return querySingle(query);
    }

    protected TypedQuery<T> buildQuery(String param, Object value) {
        EntityManager em=provider.get();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(klass);
        Root<T> root = cq.from(klass);
        cq.select(root);

        boolean hasParam = !Strings.isNullOrEmpty(param);

        /*
         * Add where condition if the parameter is not null or empty.
         */
        if (hasParam) {
            cq.where(cb.equal(root.get(param), cb.parameter(value.getClass(), param)));
        }

        TypedQuery<T> query = em.createQuery(cq);
        if (hasParam) {
            query.setParameter(param, value);
        }
        return query;
    }

    @Transactional
    protected T querySingle(TypedQuery<T> query) {
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            logger.debug("No result found for entity {}. Query: {}", klass.getSimpleName(), query);
        }
        return result;
    }

    @Transactional
    protected List<T> queryList(TypedQuery<T> query) {
        List<T> result=null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to get the result list. Query details: {}\nError: {}", query, e);
        }
        return result;
    }

    /**
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Transactional
    @Override
    public List<T> findAll(int pageSize, int pageNumber) {
        EntityManager em=provider.get();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(klass);
        Root<T> from = criteriaQuery.from(klass);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        return queryListPaginated(em.createQuery(select), pageSize, pageNumber);
    }

    protected List<T> queryListPaginated(TypedQuery<T> query, int pageSize, int pageNumber) {
        Preconditions.checkArgument(pageSize>1,"The page size should be greater than 1. Given: %s", pageSize);
        Preconditions.checkArgument(pageNumber>0,"The page number should be greater than zero. Given: %s", pageNumber);
        //total records
        Long count = size();
        int startIndex=(pageNumber - 1) * pageSize;
        Preconditions.checkState( startIndex < count.intValue(),
                "Page number too big. Total %s records, but requested entity starting at %s", count, startIndex);

        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Transactional
    @Override
    public ID getMinID() {
        EntityManager em=provider.get();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root entity = cq.from(klass);
        cq.select(cb.min(entity.get("id")));
        Query query = em.createQuery(cq);
        ID minActual = (ID) query.getSingleResult();
        if (logger.isDebugEnabled()) {
            logger.debug("{} value: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), minActual);
        }
        return minActual;
    }

    @Transactional
    @Override
    public ID getMaxID() {
        EntityManager em=provider.get();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root entity = cq.from(klass);
        cq.select(cb.max(entity.get("id")));
        Query query = em.createQuery(cq);
        ID maxValue = (ID) query.getSingleResult();
        if (logger.isDebugEnabled()) {
            logger.debug("{}.id max value: {}", tableName, maxValue);
        }
        return maxValue;
    }


    @Transactional
    @Override
    public void truncate() {
        EntityManager em=provider.get();
        Query query = em.createNativeQuery(String.format("TRUNCATE TABLE %s",tableName));
        query.executeUpdate();
    }

    @Transactional
    @Override
    public Long size() {
        EntityManager em=provider.get();
        Long count = internalCount(em,null, null);
        if (logger.isDebugEnabled()) {
            logger.debug("{} size: {}", klass.getSimpleName(), count);
        }
        return count;
    }

    @Transactional
    protected Long internalCount(EntityManager em,String param, Object value) {
        Long count;
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root= cq.from(klass);
        cq.select(cb.count(root));

        boolean hasParam = !Strings.isNullOrEmpty(param);

        /*
         * Add where condition if the parameter is not null or empty.
         */
        if (hasParam) {
            cq.where(cb.equal(root.get(param), cb.parameter(String.class, param)));
        }

        TypedQuery<Long> query = em.createQuery(cq);
        if (hasParam) {
            query.setParameter(param, value);
        }
        count = query.getSingleResult();
        if (logger.isDebugEnabled()) {
            logger.debug("{} count for param {}: {}", klass.getSimpleName(), param, count);
        }
        return count;
    }

    /**
     * The first entity in the table
     *
     * @return The first record
     */
    @Override
    public T first() {
        ID id=getMinID();
        if (id == null) {
            return null;
        }
        return findById(id);
    }

    /**
     * Gets the last entity in the table
     *
     * @return The last record
     */
    @Override
    public T last() {
        ID id=getMaxID();
        if (id == null) {
            return null;
        }
        return findById(id);
    }
}

package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.AbstractEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 22:42
 */
public interface GenericDao<T extends AbstractEntity, ID extends Serializable> {
    void create(T entity);

    void delete(T entity);

    T update(T entity);

    T findById(ID id);

    T first();

    T last();

    List<T> findAll();

    /**
     * Perform a paginated query
     *
     * @return Entities on page pageNumber
     */
    List<T> findAll(int pageSize, int pageNumber);

    ID getMinID();

    /**
     * Get the max value for the <em>id</em> column.
     *
     * @return The max id or -1 if the table is empty or no such column exists.
     */
    ID getMaxID();

    Long size();

    void truncate();

    T findSingleResult(String param, Object value);

    List<T> findResultList(String param, Object value);

    /**
     * paginated query
     *
     * @param param
     * @param value
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<T> findResultList(String param, Object value, int pageSize, int pageNumber);

}

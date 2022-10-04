package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Customer;

/**
 * baseDao contains insert, update and delete.
 * need to be inhereted by all dao interface
 * @param <T>
 */
public interface IBaseDao<T extends BaseEntity> {

    /**
     * Insertion
     */
    int insertOne(T entity);

    /**
     * updating
     */
    int updateOne(T entity);

    /**
     * deletion
     */
    int deleteOne(T entity);

    void throwConcurrencyException(T entity);
}

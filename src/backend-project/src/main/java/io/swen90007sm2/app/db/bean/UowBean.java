package io.swen90007sm2.app.db.bean;

import io.swen90007sm2.app.dao.IBaseDao;
import io.swen90007sm2.app.model.entity.BaseEntity;

import java.util.Objects;

/**
 * @author 996Worker
 * @description bean for unit of work helper
 * @create 2022-08-16 10:04
 */
public class UowBean {

    /* Reference of the entity that need modifies */
    private BaseEntity entity;
    /* Reference of the dao for the entity */
    private IBaseDao entityDao;
    /* the cache key need be deleted after updating */
    private String cacheKey;

    public UowBean() {
    }

    public UowBean(BaseEntity entity, IBaseDao entityDao, String cacheKey) {
        this.entity = entity;
        this.entityDao = entityDao;
        this.cacheKey = cacheKey;
    }

    public BaseEntity getEntity() {
        return entity;
    }

    public void setEntity(BaseEntity entity) {
        this.entity = entity;
    }

    public IBaseDao getEntityDao() {
        return entityDao;
    }

    public void setEntityDao(IBaseDao entityDao) {
        this.entityDao = entityDao;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UowBean uowBean = (UowBean) o;
        return entity.getId().equals(uowBean.entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.getId());
    }
}


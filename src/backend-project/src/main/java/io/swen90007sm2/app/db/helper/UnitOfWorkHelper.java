package io.swen90007sm2.app.db.helper;

import cn.hutool.core.collection.ConcurrentHashSet;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.dao.IBaseDao;
import io.swen90007sm2.app.db.bean.UowBean;
import io.swen90007sm2.app.db.factory.DaoFactory;
import io.swen90007sm2.app.model.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * unit of work helper, handles dirty jobs.
 *
 * Uses in service
 *
 * @author xiaotian li
 */
public class UnitOfWorkHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitOfWorkHelper.class);

    // each request will be a thread, this field will make UnitOfWorkHelper instance thread private
    private static final ThreadLocal<UnitOfWorkHelper> current = new ThreadLocal<>();

    private final Set<UowBean> newUowBeans;
    private final Set<UowBean> dirtyUowBeans;
    private final Set<UowBean> deletedUowBeans;

    private final ICacheStorage<String, Object> cacheRef;

    public UnitOfWorkHelper(ICacheStorage<String, Object> cacheRef) {
        // no need to have ConcurrentSet, because this Uow Helper is thread local
        newUowBeans = new HashSet<>();
        dirtyUowBeans = new HashSet<>();
        deletedUowBeans = new HashSet<>();
        this.cacheRef = cacheRef;
    }

    /**
     * init and get UnitOfWorkHelper. call at the start of the service, if the service needs update some data.
     * @return uow helper
     */
    public static UnitOfWorkHelper init(ICacheStorage<String, Object> cacheRef) {
        if (current.get() == null) {
            setCurrent(new UnitOfWorkHelper(cacheRef));
            return getCurrent();
        }

        return getCurrent();
    }

    public static UnitOfWorkHelper getCurrent() {
        return current.get();
    }

    public static void setCurrent(UnitOfWorkHelper uow) {
        current.set(uow);
    }

    /**
     * register a new obj that will put into database
     *
     */
    public void registerNew(BaseEntity entity, IBaseDao dao, String cacheKey) {
        Assert.notNull(entity.getId(), "entity Id must be not null");
        UowBean uowBean = new UowBean();
        uowBean.setEntity(entity);
        uowBean.setEntityDao(dao);
        uowBean.setCacheKey(cacheKey);
        Assert.isTrue(!dirtyUowBeans.contains(uowBean), "entity must be not dirty");
        Assert.isTrue(!deletedUowBeans.contains(uowBean), "entity must not be deleted");
        Assert.isTrue(!newUowBeans.contains(uowBean), "entity should not exist before");
        newUowBeans.add(uowBean);
    }

    /**
     * register a changed obj
     */
    public void registerDirty(BaseEntity entity, IBaseDao dao, String cacheKey) {
        Assert.notNull(entity.getId(), "entity Id must be not null");
        UowBean uowBean = new UowBean();
        uowBean.setEntity(entity);
        uowBean.setEntityDao(dao);
        uowBean.setCacheKey(cacheKey);
        Assert.isTrue(!deletedUowBeans.contains(uowBean), "entity must not be deleted");
        if (!dirtyUowBeans.contains(uowBean) && !newUowBeans.contains(uowBean)) {
            dirtyUowBeans.add(uowBean);
        }
    }

    /**
     * register a obj that need to be deleted
     */
    public void registerDeleted(BaseEntity entity, IBaseDao dao, String cacheKey) {
        Assert.notNull(entity.getId(), "entity Id must be not null");
        UowBean uowBean = new UowBean();
        uowBean.setEntity(entity);
        uowBean.setEntityDao(dao);
        uowBean.setCacheKey(cacheKey);
        // if try to create new one before, cancel this try
        if (newUowBeans.remove(uowBean)) return;
        // it would be removed, so don't worry about this dirty object, just remove
        dirtyUowBeans.remove(uowBean);
        deletedUowBeans.add(uowBean);
    }

    /**
     * parform CUD to database
     * <br/>
     * IMPORTANT: make sure DaoFactory contains all existing Dao!
     */
    public synchronized void commit() {

        for (UowBean bean : newUowBeans) {
            IBaseDao dao = bean.getEntityDao();
            try {
                dao.insertOne(bean.getEntity());
            } catch (Exception e) {
                LOGGER.error("Uow insertion error: ", e);
            }
        }

        for (UowBean bean : dirtyUowBeans) {
            IBaseDao dao = bean.getEntityDao();
            try {
                // update db
                dao.updateOne(bean.getEntity());
                // Cache evict model
                // clean the cache after update the db
                cacheRef.remove(bean.getCacheKey());
            } catch (Exception e) {
                LOGGER.error("Uow update error: ", e);
            }
        }

        for (UowBean bean : deletedUowBeans) {
            IBaseDao dao = bean.getEntityDao();
            try {
                // update db
                dao.deleteOne(bean.getEntity());
                cacheRef.remove(bean.getCacheKey());
                // Cache evict model
                // clean the cache after update the db
                cacheRef.remove(bean.getCacheKey());
            } catch (Exception e) {
                LOGGER.error("Uow deletion error: ", e);
            }
        }
    }
}

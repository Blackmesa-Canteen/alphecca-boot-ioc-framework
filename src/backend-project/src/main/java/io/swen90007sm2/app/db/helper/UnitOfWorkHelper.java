package io.swen90007sm2.app.db.helper;

import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.dao.IBaseDao;
import io.swen90007sm2.app.db.bean.UowBean;
import io.swen90007sm2.app.model.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    private final Map<String, BaseEntity> backupOldBeanMap;
    private final Set<UowBean> deletedUowBeans;

    private final ICacheStorage<String, Object> cacheRef;

    public UnitOfWorkHelper(ICacheStorage<String, Object> cacheRef) {
        // no need to have ConcurrentSet, because this Uow Helper is thread local
        newUowBeans = new LinkedHashSet<>();
        dirtyUowBeans = new LinkedHashSet<>();
        backupOldBeanMap = new LinkedHashMap<>();
        deletedUowBeans = new LinkedHashSet<>();
        this.cacheRef = cacheRef;
    }

    /**
     * init and get UnitOfWorkHelper. call at the start of the service, if the service needs update some data.
     * @return uow helper
     */
    public static UnitOfWorkHelper init(ICacheStorage<String, Object> cacheRef) {
        if (current.get() == null) {
            setCurrent(new UnitOfWorkHelper(cacheRef));
            LOGGER.info("Unit of work loaded.");
            // init database connection
            DbHelper.initConnection();
            LOGGER.info("database connection established for this request");
            return current.get();
        }

        LOGGER.info("Unit of work loaded.");
        // init database connection
        DbHelper.initConnection();
        LOGGER.info("database connection established for this request");

        return current.get();
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
    public void registerNew(BaseEntity entity, IBaseDao dao) {
        Assert.notNull(entity.getId(), "entity Id must be not null");
        UowBean uowBean = new UowBean();
        uowBean.setEntity(entity);
        uowBean.setEntityDao(dao);
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
     */
    public void commit() {
        // class lock to guarantee CRUD Atomicity for one request
        synchronized (UnitOfWorkHelper.class) {
            for (UowBean bean : newUowBeans) {
                IBaseDao dao = bean.getEntityDao();
                try {
                    dao.insertOne(bean.getEntity());
                } catch (Exception e) {
                    LOGGER.error("Uow insertion error: ", e);
                    // continue throw e to upper
                    throw e;
                }
            }

            for (UowBean bean : dirtyUowBeans) {
                IBaseDao dao = bean.getEntityDao();
                try {
                    // record the old one from identity map
                    if (cacheRef.get(bean.getCacheKey()).isPresent()) {
                        backupOldBeanMap.put(bean.getCacheKey(),(BaseEntity) cacheRef.get(bean.getCacheKey()).get());
                    }

                    // update db
                    dao.updateOne(bean.getEntity());

                    // Cache evict model
                    // clean the cache after update the db
                    cacheRef.remove(bean.getCacheKey());
                } catch (Exception e) {
                    LOGGER.error("Uow update error: ", e);
                    // continue throw e to upper
                    throw e;
                }
            }

            for (UowBean bean : deletedUowBeans) {
                IBaseDao dao = bean.getEntityDao();
                try {
                    // update db
                    dao.deleteOne(bean.getEntity());
                    // Cache evict model
                    // clean the cache after update the db
                    cacheRef.remove(bean.getCacheKey());
                } catch (Exception e) {
                    LOGGER.error("Uow deletion error: ", e);
                    // continue throw e to upper
                    throw e;
                }
            }

            newUowBeans.clear();
            deletedUowBeans.clear();
            dirtyUowBeans.clear();

            // commit the transaction
            try {
                // JDBC db transaction commit
                DbHelper.getConnection().commit();
                LOGGER.info("Unit of work committed.");
            } catch (SQLException e) {
                throw new InternalException(e.getMessage());
            } finally {
                DbHelper.closeConnection();
                DbHelper.setConnection(null);
            }
        }
    }

    /**
     * perform rollback logic
     */
    public void rollback() {

        newUowBeans.clear();
        deletedUowBeans.clear();
        dirtyUowBeans.clear();
        backupOldBeanMap.clear();
        try {
            // JDBC db transaction rollback
            DbHelper.getConnection().rollback();
            LOGGER.info("Unit of work has rollback changes in request transaction.");
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        } finally {
            DbHelper.closeConnection();
            DbHelper.setConnection(null);
        }
    }
}

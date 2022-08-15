package io.swen90007sm2.app.db.helper;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.dao.IBaseDao;
import io.swen90007sm2.app.db.factory.DaoFactory;
import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * unit of work helper
 *
 * @author xiaotian li
 */
public class UnitOfWorkHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitOfWorkHelper.class);

    // each request will be a thread
    private static final ThreadLocal<UnitOfWorkHelper> current = new ThreadLocal<>();

    private final Set<BaseEntity> newObjects;
    private final Set<BaseEntity> dirtyObjects;
    private final Set<BaseEntity> deletedObjects;

    public UnitOfWorkHelper() {
        newObjects = new HashSet<>();
        dirtyObjects = new HashSet<>();
        deletedObjects = new HashSet<>();
    }

    public static void init() {
        setCurrent(new UnitOfWorkHelper());
    }

    public static UnitOfWorkHelper getCurrent() {
        return current.get();
    }

    public static void setCurrent(UnitOfWorkHelper uow) {
        current.set(uow);
    }

    public void registerNew(BaseEntity obj) {
        Assert.notNull(obj.getId(), "entity Id must be not null");
        Assert.isTrue(!dirtyObjects.contains(obj), "entity must be not dirty");
        Assert.isTrue(!deletedObjects.contains(obj), "entity must not be deleted");
        Assert.isTrue(!newObjects.contains(obj), "entity should not exist before");
        newObjects.add(obj);
    }

    public void registerDirty(BaseEntity obj) {
        Assert.notNull(obj.getId(), "entity Id must be not null");
        Assert.isTrue(!deletedObjects.contains(obj), "entity must not be deleted");
        if (!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
            dirtyObjects.add(obj);
        }
    }

    public void registerDeleted(BaseEntity obj) {
        Assert.notNull(obj.getId(), "entity Id must be not null");
        // if try to create new one before, cancel this try
        if (newObjects.remove(obj)) return;
        // it would be removed, so don't worry about this dirty object, just remove
        dirtyObjects.remove(obj);
        deletedObjects.add(obj);
    }

    public void commit() {
        for (BaseEntity obj : newObjects) {
            IBaseDao dao = DaoFactory.getDao(obj.getClass());
            if (dao != null) {
                dao.insertOne(obj);
            } else {
                LOGGER.error("dao for insertion should be not null, DaoFactory missing something.");
            }
        }

        for (BaseEntity obj : dirtyObjects) {
            IBaseDao dao = DaoFactory.getDao(obj.getClass());
            if (dao != null) {
                dao.updateOne(obj);
            } else {
                LOGGER.error("dao for updating should be not null, DaoFactory missing something.");
            }
        }

        for (BaseEntity obj : deletedObjects) {
            IBaseDao dao = DaoFactory.getDao(obj.getClass());
            if (dao != null) {
                dao.deleteOne(obj);
            } else {
                LOGGER.error("dao for deletion should be not null, DaoFactory missing something.");
            }
        }
    }
}

package io.swen90007sm2.app.lock;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.lock.constant.LockConstant;
import io.swen90007sm2.app.lock.dao.IResourceUserLockDao;
import io.swen90007sm2.app.lock.entity.ResourceUserLock;
import io.swen90007sm2.app.lock.exception.ResourceConflictException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @description exclusive Read Write lock for multiple user shared resource
 * @create 2022-09-20 16:38
 */
@Component(beanName = LockConstant.EXCLUSIVE_LOCK_MANAGER)
public class ExclusiveResourceUserLockManager implements IResourceUserLockManager {

    @AutoInjected
    IResourceUserLockDao resourceUserLockDao;

    @Override
    public synchronized void acquire(String resourceId, String userId) throws ResourceConflictException {
        ResourceUserLock existingLock = resourceUserLockDao.findOneLockByResourceId(resourceId);
        if (existingLock != null) {

            // check expire time
            Date now = TimeUtil.now();
            Date lockCreateTime = existingLock.getCreateTime();
            if (TimeUtil.getDeltaBetweenDate(now, lockCreateTime, TimeUnit.MILLISECONDS) > LockConstant.LOCK_EXPIRE_MS) {

                // the lock is expired, remove it to prevent deadlock
                resourceUserLockDao.deleteOneByResourceId(resourceId);
                throw new ResourceConflictException("Resource Lock: the public exclusive data is accessed by the other user, " +
                        "please try again later");
            }

        }

        ResourceUserLock lock = new ResourceUserLock(
                resourceId
        );

        resourceUserLockDao.insertOne(lock);
    }

    @Override
    public synchronized void release(String resourceId, String userId) throws ResourceConflictException{
        resourceUserLockDao.deleteOneByResourceId(resourceId);
    }
}
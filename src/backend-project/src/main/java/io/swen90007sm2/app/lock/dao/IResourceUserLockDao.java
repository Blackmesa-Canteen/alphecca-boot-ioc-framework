package io.swen90007sm2.app.lock.dao;

import io.swen90007sm2.app.lock.entity.ResourceUserLock;

public interface IResourceUserLockDao {

    ResourceUserLock findOneLockByResourceId(Integer resourceId);

    int insertOne(ResourceUserLock entity);

    int deleteOneByResourceId(Integer resourceId);

    int deleteOneByResourceAndUser(Integer resourceId, String userId);
}

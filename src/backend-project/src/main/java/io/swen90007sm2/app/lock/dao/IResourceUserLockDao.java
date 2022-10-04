package io.swen90007sm2.app.lock.dao;

import io.swen90007sm2.app.lock.entity.ResourceUserLock;

public interface IResourceUserLockDao {

    ResourceUserLock findOneLockByResourceId(String resourceId);

    int insertOne(ResourceUserLock entity);

    int deleteOneByResourceId(String resourceId);

    int deleteOneByResourceAndUser(String resourceId, String userId);
}

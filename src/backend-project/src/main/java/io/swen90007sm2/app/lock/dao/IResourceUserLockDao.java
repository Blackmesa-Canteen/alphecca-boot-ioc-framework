package io.swen90007sm2.app.lock.dao;

import io.swen90007sm2.app.lock.entity.ResourceUserLock;

public interface IResourceUserLockDao {

    int insertOne(ResourceUserLock entity);

    int deleteOne(Integer resourceId, String userId);
}

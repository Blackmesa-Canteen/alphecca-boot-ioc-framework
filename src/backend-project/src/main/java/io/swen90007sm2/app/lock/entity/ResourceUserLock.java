package io.swen90007sm2.app.lock.entity;

import io.swen90007sm2.app.lock.constant.LockConstant;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-20 16:40
 */
public class ResourceUserLock {

    String resourceId;

    String userId;

    Integer lockType = LockConstant.EXCLUSIVE_LOCK;


    public ResourceUserLock() {
    }

    public ResourceUserLock(String resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceUserLock(String resourceId, String userId) {
        this.resourceId = resourceId;
        this.userId = userId;
    }

    public ResourceUserLock(String resourceId, String userId, Integer lockType) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.lockType = lockType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
    }
}
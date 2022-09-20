package io.swen90007sm2.app.lock.entity;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-20 16:40
 */
public class ResourceUserLock {

    /**
     * These 2 field need to use Compose Unique index in the database
     */
    Integer resourceId;

    String userId;


    public ResourceUserLock() {
    }

    public ResourceUserLock(Integer resourceId, String userId) {
        this.resourceId = resourceId;
        this.userId = userId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
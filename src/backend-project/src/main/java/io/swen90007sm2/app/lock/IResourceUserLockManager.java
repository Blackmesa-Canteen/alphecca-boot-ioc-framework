package io.swen90007sm2.app.lock;

import io.swen90007sm2.app.lock.exception.ResourceConflictException;
import org.apache.commons.lang3.concurrent.ConcurrentException;

/**
 * a lock manager for multiple user shared resources
 */
public interface IResourceUserLockManager {
    void acquire(String resourceId, String userId) throws ResourceConflictException;
    void release(String resourceId, String userId) throws ResourceConflictException;
}

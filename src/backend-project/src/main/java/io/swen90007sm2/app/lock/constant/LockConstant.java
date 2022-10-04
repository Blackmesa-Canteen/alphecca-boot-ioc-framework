package io.swen90007sm2.app.lock.constant;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-21 10:46
 */
public interface LockConstant {

    String EXCLUSIVE_LOCK_MANAGER = "ex-lm";
    String READ_WRITE_LOCK_MANAGER = "rw-lm";
    Integer EXCLUSIVE_LOCK = 0;
    Integer SHARED_READ_LOCK = 1;
    Integer SHARED_WRITE_LOCK = 2;

    // resource lock will expire in 2 minute to prevent deadlock
    Integer LOCK_EXPIRE_MS = 2 * 60 * 1000;
}
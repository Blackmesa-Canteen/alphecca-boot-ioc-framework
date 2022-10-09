package io.swen90007sm2.app.lock.exception;

import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-20 16:44
 */
public class ResourceConflictException extends InternalException {

    public ResourceConflictException(String msg) {
        super(msg, StatusCodeEnume.CONCURRENCY_EXCEPTION.getCode());
    }
}
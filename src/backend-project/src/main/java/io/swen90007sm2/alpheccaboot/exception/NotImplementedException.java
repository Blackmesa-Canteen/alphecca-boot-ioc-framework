package io.swen90007sm2.alpheccaboot.exception;

import org.apache.http.HttpStatus;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-29 15:11
 */
public class NotImplementedException extends InternalException {
    private static final long serialVersionUID = 1L;

    public NotImplementedException() {
        super("API not implemented", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
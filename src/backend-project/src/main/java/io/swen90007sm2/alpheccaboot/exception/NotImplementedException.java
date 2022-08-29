package io.swen90007sm2.alpheccaboot.exception;

import org.apache.http.HttpStatus;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-29 15:11
 */
public class NotImplementedException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg = "API not implemented";
    private int code = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public NotImplementedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NotImplementedException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public NotImplementedException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public NotImplementedException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
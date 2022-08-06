/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.swen90007sm2.alpheccaboot.exception;

import org.apache.http.HttpStatus;

/**
 * customized Exception
 *
 * @author xiaotian
 */
public class RequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = HttpStatus.SC_BAD_REQUEST;

    public RequestException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public RequestException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public RequestException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public RequestException(String msg, int code, Throwable e) {
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

/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.swen90007sm2.alpheccaboot.exception;

import org.apache.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * customized Exception
 *
 * @author xiaotian
 */
public class ValidationException extends ConstraintViolationException {

	private static final long serialVersionUID = 1L;

	private String msg = "Illegal request parameters.";
	private int code = HttpStatus.SC_BAD_REQUEST;


	public ValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(message, constraintViolations);
	}

	public ValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(constraintViolations);
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

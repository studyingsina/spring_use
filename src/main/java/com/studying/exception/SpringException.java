/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 * 酒店后台研发.
 */
package com.studying.exception;

/**
 * Desc
 * 
 * @author: zhangjunwei@meituan.com
 * @Date: 2015年2月14日
 */
public class SpringException extends RuntimeException {

	private static final long serialVersionUID = 1682074063680883189L;

	private String exceptionMsg;

	public SpringException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionMsg() {
		return this.exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

}

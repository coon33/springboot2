package com.allwell.core.exception;

public class OperationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public OperationException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public OperationException(String msg) {
		this.msg = msg;
	}

	private int code = 500;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

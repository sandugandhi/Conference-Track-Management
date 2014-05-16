package com.techconf.exception;

/*
 * Exception class for invalid Talk.
 */

public class SchedulerException extends Exception {
	private static final long serialVersionUID = 1L;

	public SchedulerException(String msg) {
		super(msg);
	}

}

package com.dfremont.simplebatch.core;

public class ExecutionReport implements Executable {
	public static final String TERMINATED = "TERMINATED";
	public static final String NOT_STARTED = "NOT_STARTED";
	public static final String STARTED = "STARTED";
	String status = NOT_STARTED;
	String execution;

	public String getStatus() {
		return status;
	}

	public String getExecution() {
		return execution;
	}

}

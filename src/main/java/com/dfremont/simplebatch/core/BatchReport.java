package com.dfremont.simplebatch.core;

public class BatchReport {
	public static final String TERMINATED = "TERMINATED";
	public static final String NOT_STARTED = "NOT_STARTED";
	public static final String STARTED = "STARTED";

	private String status = NOT_STARTED;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package com.dfremont.simplebatch.core;

import java.util.List;

// TODO jobparametersvalidator
public class BatchProcess {
	String name;
	List<Step<?, ?>> steps;
	final ExecutionReport report = new ExecutionReport();

	public BatchProcess(List<Step<?, ?>> steps) {
		this.steps = steps;
	}

	public BatchProcess(List<Step<?, ?>> steps, String name) {
		this(steps);
		this.name = name;
	}

	public final void execute() throws Exception {
		report.status = ExecutionReport.STARTED;
		for (Step<?, ?> currentStep : steps) {
			currentStep.execute();
			report.execution += currentStep.getExecution();
		}
		report.status = ExecutionReport.TERMINATED;
	}

	public ExecutionReport getReport() {
		return report;
	}
}

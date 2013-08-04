package com.dfremont.simplebatch.core;

import java.util.List;

// TODO compatibility with springbatch
// TODO jobparametersvalidator
public class BatchProcess {
	String name;
	// TODO private boolean restartable = true;
	List<Step<?, ?>> steps;

	public BatchProcess(List<Step<?, ?>> steps) {
		this.steps = steps;
	}

	public BatchProcess(List<Step<?, ?>> steps, String name) {
		this(steps);
		this.name = name;
	}

	public final void execute() throws Exception {
		for (Step<?, ?> currentStep : steps) {
			currentStep.execute();
		}
	}
}

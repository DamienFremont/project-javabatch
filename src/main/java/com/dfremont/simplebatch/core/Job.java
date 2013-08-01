package com.dfremont.simplebatch.core;

import java.util.List;

// TODO compatibility with springbatch
// TODO jobparametersvalidator
public class Job {
	// TODO String name = "Undefined";
	// TODO private boolean restartable = true;
	List<JobStep<?, ?>> steps;

	public Job(List<JobStep<?, ?>> steps) {
		this.steps = steps;
	}

	public final void execute() throws Exception {
		for (JobStep<?, ?> currentStep : steps) {
			currentStep.execute();
		}
	}
}

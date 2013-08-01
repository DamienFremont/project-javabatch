package com.dfremont.simplebatch.core;

import java.util.List;

// TODO compatibility with springbatch
// TODO jobparametersvalidator
public class Job {
	String name;
	// TODO private boolean restartable = true;
	List<JobStep<?, ?>> steps;

	public Job(List<JobStep<?, ?>> steps) {
		this.steps = steps;
	}

	public Job(List<JobStep<?, ?>> steps, String name) {
		this(steps);
		this.name = name;
	}

	public final void execute() throws Exception {
		for (JobStep<?, ?> currentStep : steps) {
			currentStep.execute();
		}
	}
}

package com.dfremont.simplebatch;

import com.dfremont.simplebatch.core.Job;
import com.dfremont.simplebatch.core.Report;

public class BatchRunner {

	Job job;

	// TODO add args validator
	public BatchRunner(Job job) {
		this.job = job;
	}

	public Report run(Object... args) throws Exception {
		job.execute();
		return null; // TODO return report
	}
}

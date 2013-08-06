package com.dfremont.simplebatch;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.BatchReport;

public class BatchRunner {
	BatchProcess job;

	// TODO add args validator
	public BatchRunner(BatchProcess job) {
		this.job = job;
	}

	public BatchReport run(Object... args) throws Exception {
		job.execute();
		return job.getReport();
	}
}

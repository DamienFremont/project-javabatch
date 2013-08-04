package com.dfremont.simplebatch;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.Report;

public class BatchRunner {

	BatchProcess job;

	// TODO add args validator
	public BatchRunner(BatchProcess job) {
		this.job = job;
	}

	public Report run(Object... args) throws Exception {
		job.execute();
		return null; // TODO return report
	}
}

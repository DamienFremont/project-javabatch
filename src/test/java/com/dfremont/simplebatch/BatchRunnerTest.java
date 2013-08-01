package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dfremont.simplebatch.core.Job;
import com.dfremont.simplebatch.core.JobStep;

public class BatchRunnerTest {

	@Test
	public void testBatchRunner() throws Exception {
		List<JobStep<?, ?>> steps = new ArrayList<JobStep<?, ?>>();
		Job job = new Job(steps);
		BatchRunner classToTest = new BatchRunner(job);
		classToTest.run();
	}

}

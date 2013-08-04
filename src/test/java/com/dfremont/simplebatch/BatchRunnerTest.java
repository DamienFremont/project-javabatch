package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.Step;

public class BatchRunnerTest {

	@Test
	public void testBatchRunner() throws Exception {
		List<Step<?, ?>> steps = new ArrayList<Step<?, ?>>();
		BatchProcess job = new BatchProcess(steps);
		BatchRunner classToTest = new BatchRunner(job);
		classToTest.run();
	}

}

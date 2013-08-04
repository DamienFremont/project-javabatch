package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.BatchReport;
import com.dfremont.simplebatch.core.Step;

public class BatchRunnerTest {

	@Test
	public void testBatchRunner() throws Exception {
		// arrange
		List<Step<?, ?>> steps = new ArrayList<Step<?, ?>>();
		BatchProcess job = new BatchProcess(steps);
		BatchRunner classToTest = new BatchRunner(job);
		// act
		BatchReport resut = classToTest.run();
		// assert
		assertThat(resut).isNotNull();
		assertThat(resut.getStatus()).isNotNull().isEqualTo(
				BatchReport.TERMINATED);
	}

}

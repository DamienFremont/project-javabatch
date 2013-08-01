package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Job;
import com.dfremont.simplebatch.core.JobStep;

public class BatchRunnerFluent {

	List<JobStep<?, ?>> steps = new ArrayList<JobStep<?, ?>>();
	Job job;

	private BatchRunnerFluent(String name) {
		job = new Job(steps, name);
	}

	public static BatchRunnerFluent createBatch() {
		return new BatchRunnerFluent(null);
	}

	public static BatchRunnerFluent createBatch(String name) {
		return new BatchRunnerFluent(name);
	}

	public BatchRunnerFluent setReader(ItemReader<Object> mockReader) {
		// TODO Auto-generated method stub
		return this;
	}

	public BatchRunnerFluent setProcessor(
			ItemProcessor<Object, Object> mockProcessor) {
		// TODO Auto-generated method stub
		return this;
	}

	public BatchRunnerFluent setWriter(ItemWriter<Object> mockWriter) {
		// TODO Auto-generated method stub
		return this;
	}

	public BatchRunnerFluent setCommitInterval(int i) {
		// TODO Auto-generated method stub
		return this;
	}

	public BatchRunnerFluent run() {
		// TODO Auto-generated method stub
		return this;
	}

}

package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.BatchReport;
import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Step;

public class BatchRunnerFluent {

	List<Step<?, ?>> steps = new ArrayList<Step<?, ?>>();
	BatchProcess job;
	private ItemReader<?> defaultReader;
	private ItemWriter<?> defaultWriter;
	private ItemProcessor<?, ?> defaultProcessor;

	private BatchRunnerFluent(String name) {
		job = new BatchProcess(steps, name);
	}

	public static BatchRunnerFluent createBatch() {
		return new BatchRunnerFluent(null);
	}

	public static BatchRunnerFluent createBatch(String name) {
		return new BatchRunnerFluent(name);
	}

	public BatchRunnerFluent setReader(ItemReader<?> mockReader) {
		defaultReader = mockReader;
		return this;
	}

	public BatchRunnerFluent setProcessor(ItemProcessor<?, ?> mockProcessor) {
		defaultProcessor = mockProcessor;
		return this;
	}

	public BatchRunnerFluent setWriter(ItemWriter<?> mockWriter) {
		defaultWriter = mockWriter;
		return this;
	}

	public BatchRunnerFluent setCommitInterval(int i) {
		// TODO Auto-generated method stub
		return this;
	}

	public BatchRunnerFluent run() throws Exception {
		if (steps.isEmpty()) {
			steps.add(new Step(defaultReader, defaultProcessor, defaultWriter));
		}
		job.execute();
		return this;
	}

	public BatchReport getReport() {
		return job.getReport();
	}

}

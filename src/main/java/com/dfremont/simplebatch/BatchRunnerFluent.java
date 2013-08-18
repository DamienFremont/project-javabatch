package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.ExecutionReport;
import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Step;

public class BatchRunnerFluent {
	List<Step<?, ?>> steps = new ArrayList<Step<?, ?>>();
	BatchProcess job;
	BatchRunnerFluentStep defaultStep = new BatchRunnerFluentStep();

	private BatchRunnerFluent(String name) {
		job = new BatchProcess(steps, name);
	}

	public static BatchRunnerFluent createBatch() {
		return new BatchRunnerFluent(null);
	}

	public static BatchRunnerFluent createBatch(String name) {
		return new BatchRunnerFluent(name);
	}

	public BatchRunnerFluent setReader(ItemReader<?> reader) {
		defaultStep.setReader(reader);
		return this;
	}

	public BatchRunnerFluent setProcessor(ItemProcessor<?, ?> processor) {
		defaultStep.setProcessor(processor);
		return this;
	}

	public BatchRunnerFluent setWriter(ItemWriter<?> writer) {
		defaultStep.setWriter(writer);
		return this;
	}

	public BatchRunnerFluent setCommitInterval(int i) {
		// TODO Auto-generated method stub
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BatchRunnerFluent run() throws Exception {
		if (steps.isEmpty()) { // dirty mode
			steps.add(defaultStep.build());
		}
		job.execute();
		return this;
	}

	public ExecutionReport getReport() {
		return job.getReport();
	}

	public static BatchRunnerFluentStep createStep() {
		return new BatchRunnerFluentStep();
	}

	public static BatchRunnerFluentStep createStep(String name) {
		return new BatchRunnerFluentStep(name);
	}

	public BatchRunnerFluent addStep(BatchRunnerFluentStep newStep) {
		steps.add(newStep.build());
		return this;
	}

}

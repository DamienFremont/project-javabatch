package com.dfremont.simplebatch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dfremont.simplebatch.core.BatchProcess;
import com.dfremont.simplebatch.core.ExecutionReport;
import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Step;
import com.dfremont.simplebatch.infra.file.FileItemReader;
import com.dfremont.simplebatch.infra.file.FileItemWriter;
import com.dfremont.simplebatch.infra.file.FileLineMapper;

public class BatchRunnerFluent {
	List<Step<?, ?>> steps = new ArrayList<Step<?, ?>>();
	BatchProcess job;
	BatchRunnerFluentStep defaultStep = new BatchRunnerFluentStep();

	private BatchRunnerFluent(String name) {
		job = new BatchProcess(steps, name);
	}

	// basic api

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

	// extended api

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

	// full fluent api

	public BatchRunnerFluent setFileReader(String file, String separator)
			throws FileNotFoundException {
		defaultStep.setReader(new FileItemReader<List<String>>(file,
				new FileLineMapper<List<String>>("{0},{1},{2}", ",")));
		return this;
	}

	public BatchRunnerFluent setFileWriter(String file, String pattern) throws IOException {
		defaultStep.setWriter(new FileItemWriter<List<String>>(
				"out_filtered.txt", new FileLineMapper<List<String>>(
						"{0},{1},{2}", ",")));
		return this;
	}
}

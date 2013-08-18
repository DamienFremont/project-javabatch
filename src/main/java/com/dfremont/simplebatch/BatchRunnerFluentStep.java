package com.dfremont.simplebatch;

import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Step;

public class BatchRunnerFluentStep {

	ItemReader<?> reader;
	ItemWriter<?> writer;
	ItemProcessor<?, ?> processor;
	String name;

	public BatchRunnerFluentStep(String name) {
		this.name = name;
	}

	public BatchRunnerFluentStep() {
	}

	public BatchRunnerFluentStep setReader(ItemReader<?> mockReader) {
		reader = mockReader;
		return this;
	}

	public BatchRunnerFluentStep setProcessor(ItemProcessor<?, ?> mockProcessor) {
		processor = mockProcessor;
		return this;
	}

	public BatchRunnerFluentStep setWriter(ItemWriter<?> mockWriter) {
		writer = mockWriter;
		return this;
	}

	public BatchRunnerFluentStep setCommitInterval(int i) {
		// TODO Auto-generated method stub
		return this;
	}

	public Step<?, ?> build() {
		return new Step(reader, processor, writer); // FIXME f*** generic
	}

}

package com.dfremont.simplebatch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;

public class BatchRunnerFluentTest {

	@SuppressWarnings("unchecked")
	ItemReader<Object> mockReader = Mockito.mock(ItemReader.class);
	@SuppressWarnings("unchecked")
	ItemProcessor<Object, Object> mockProcessor = Mockito
			.mock(ItemProcessor.class);
	@SuppressWarnings("unchecked")
	ItemWriter<Object> mockWriter = Mockito.mock(ItemWriter.class);

	@Test
	public void test_init_basic() {
		BatchRunnerFluent.createBatch() //
				.setReader(mockReader) //
				.setWriter(mockWriter) //
				.run();
	}

	@Test
	public void test_init_extended() {
		BatchRunnerFluent.createBatch("MyJob") //
				.setReader(mockReader) //
				.setProcessor(mockProcessor) //
				.setWriter(mockWriter) //
				.setCommitInterval(10) //
				.run();
	}

	@Test
	public void test_init_full() {
		fail("not yet impl");
	}
	
	@Test
	public void test_inti_of_childs() {
		fail("not yet impl");
	}
}

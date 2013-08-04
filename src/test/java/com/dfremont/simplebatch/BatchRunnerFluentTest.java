package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.mockito.Mockito;

import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;

@SuppressWarnings("unchecked")
public class BatchRunnerFluentTest {

	ItemReader<Object> mockReader = Mockito.mock(ItemReader.class);
	ItemProcessor<Object, Object> mockProcessor = Mockito
			.mock(ItemProcessor.class);
	ItemWriter<Object> mockWriter = Mockito.mock(ItemWriter.class);

	@Test
	public void test_api_lvl1_basic() {
		BatchRunnerFluent.createBatch() //
				.setReader(mockReader) //
				.setWriter(mockWriter) //
				.run();
	}

	@Test
	public void test_api_lvl2_normal() {
		BatchRunnerFluent.createBatch("MyJob") //
				.setReader(mockReader) //
				.setProcessor(mockProcessor) //
				.setWriter(mockWriter) //
				.setCommitInterval(10) //
				.run();
	}

	@Test
	public void test_api_report() {
		assertThat( //
				BatchRunnerFluent.createBatch() //
						.setReader(mockReader) //
						.setWriter(mockWriter) //
						.run().getReport() //
						.getStatus()) //
				.isNotNull();
	}

	@Test
	public void test_init_childs() {
		fail("not yet impl");
	}
}

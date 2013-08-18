package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;

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
	public void test_api_lvl1_basic() throws Exception {
		BatchRunnerFluent.createBatch() //
				.setReader(mockReader) //
				.setWriter(mockWriter) //
				.run();
	}

	@Test
	public void test_api_lvl2_normal() throws Exception {
		// act
		BatchRunnerFluent batch = BatchRunnerFluent.createBatch("MyJob") //
				.setReader(mockReader) //
				.setProcessor(mockProcessor) //
				.setWriter(mockWriter) //
				.setCommitInterval(10) //
				.run();
		// assert
		assertThat(batch).isNotNull();
		assertThat(batch.defaultReader).isNotNull();
		assertThat(batch.defaultProcessor).isNotNull();
		assertThat(batch.defaultWriter).isNotNull();
		assertThat(batch.steps).isNotEmpty();
		assertThat(batch.job).isNotNull();
		batch.run();
		assertThat(batch.steps).isNotEmpty().hasSize(1);
		assertThat(batch.steps.get(0)).isNotNull();
	}

	@Test
	public void test_api_report() throws Exception {
		assertThat( //
				BatchRunnerFluent.createBatch() //
						.setReader(mockReader) //
						.setWriter(mockWriter) //
						.run().getReport() //
						.getStatus()) //
				.isNotNull();
	}
}

package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;

import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.core.ItemReader;
import com.dfremont.simplebatch.core.ItemWriter;
import com.dfremont.simplebatch.core.Step;

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
				.setCommitInterval(10);
		// assert
		assertThat(batch).isNotNull();
		assertThat(batch.defaultStep).isNotNull();
		assertThat(batch.steps).isEmpty();
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

	@SuppressWarnings("rawtypes")
	@Test
	public void test_api_lvl3_multiples_steps() throws Exception {
		// arrange
		ItemReader<Object> mockReader1 = Mockito.mock(ItemReader.class);
		ItemWriter<Object> mockWriter1 = Mockito.mock(ItemWriter.class);
		// act
		BatchRunnerFluent batch = BatchRunnerFluent.createBatch() //
				.addStep( //
						BatchRunnerFluent.createStep() //
								.setReader(mockReader1) //
								.setWriter(mockWriter1)) //
				.addStep( //
						BatchRunnerFluent.createStep("full") //
								.setReader(mockReader) //
								.setProcessor(mockProcessor) //
								.setWriter(mockWriter)) //
				.run();
		// assert
		assertThat(batch.steps).isNotEmpty().hasSize(2);
		assertThat(batch.steps.get(0)).isNotNull();
		assertThat(batch.steps.get(1)).isNotNull();
		assertThat(batch.steps.get(0)).isNotSameAs((Step) batch.steps.get(1)); // FIXME
		verify(mockReader1).read();
		verify(mockWriter1).write(anyList());
		verify(mockReader).read();
		verify(mockProcessor, times(0)).process(any());
		verify(mockWriter).write(anyList());
	}
}

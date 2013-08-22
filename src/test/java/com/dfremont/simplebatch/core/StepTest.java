package com.dfremont.simplebatch.core;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class StepTest {

	Step<Object, Object> classToTest;
	ItemReader<Object> mockReader = Mockito.mock(ItemReader.class);
	ItemProcessor<Object, Object> mockProcessor = Mockito
			.mock(ItemProcessor.class);
	ItemWriter<Object> mockWriter = Mockito.mock(ItemWriter.class);

	@Test
	public void test_execute_without_processor() throws Exception {
		// arrange
		classToTest = new Step(mockReader, null, mockWriter);
		initMocks();
		// act
		classToTest.execute();
		// assert
		verify(classToTest.reader, Mockito.times(3)).read();
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}

	@Test
	public void test_execute_transform_by_processor() throws Exception {
		// arrange
		classToTest = new Step(mockReader, mockProcessor, mockWriter);
		Object mockedItem1 = initMocks();
		// act
		classToTest.execute();
		// assert
		verify(classToTest.reader, Mockito.times(3)).read();
		verify(classToTest.processor, Mockito.times(2)).process(mockedItem1);
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}

	private Object initMocks() throws Exception {
		Object mockedItem1 = new Object();
		Object mockedItem1processed = new Object();
		when(mockReader.read()) //
				.thenReturn(mockedItem1) //
				.thenReturn(mockedItem1) //
				.thenReturn(null);
		when(mockProcessor.process(mockedItem1)) //
				.thenReturn(mockedItem1processed);
		return mockedItem1;
	}

	@Test
	public void test_commit() throws Exception {
		// arrange
		classToTest = new Step(mockReader, null, mockWriter);
		initMocks();
		// act
		classToTest.execute(2);
		// assert
		verify(classToTest.reader, Mockito.times(2)).read();
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_init_error_without_reader_nor_writer() {
		classToTest = new Step(null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_init_error_without_writer() {
		classToTest = new Step(mockReader, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_init_error_without_reader() {
		classToTest = new Step(null, null, mockWriter);
	}
}

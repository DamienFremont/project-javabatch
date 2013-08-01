package com.dfremont.simplebatch.core;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JobStepTest {

	JobStep<Object, Object> classToTest;
	ItemReader<Object> mockReader = Mockito.mock(ItemReader.class);
	ItemProcessor<Object, Object> mockProcessor = Mockito
			.mock(ItemProcessor.class);
	ItemWriter<Object> mockWriter = Mockito.mock(ItemWriter.class);

	@Test
	public void test_nonregression_on_constructor_0_args_is_private_and_forbidden()
			throws Exception {
		try {
			JobStep.class.getConstructor();
			fail("expected error");
		} catch (NoSuchMethodException e) {
		}
		try {
			Constructor<?> con = JobStep.class.getDeclaredConstructor();
			con.setAccessible(true);
			con.newInstance();
			fail("expected error");
		} catch (InvocationTargetException e) {
		}
	}

	@Test
	public void test_nonregression_on_constructor_2_args_wants_reader_and_writer()
			throws Exception {
		try {
			new JobStep(null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep(mockReader, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep(null, mockWriter);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		new JobStep(mockReader, mockWriter);
	}

	@Test
	public void test_nonregression_on_constructor_3_args_wants_reader_and_writer_and_perhaps_processor()
			throws Exception {
		try {
			new JobStep(null, null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep(mockReader, null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep(null, null, mockWriter);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		new JobStep(mockReader, null, mockWriter);
		new JobStep(mockReader, mockProcessor, mockWriter);
	}

	@Test
	public void testExecuteWithoutProcessor() throws Exception {
		// arrange
		classToTest = new JobStep(mockReader, mockWriter);
		initMocks();
		// act
		classToTest.execute();
		// assert
		verify(classToTest.reader, Mockito.times(3)).read();
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}


	@Test
	public void testExecuteTransformByProcessor() throws Exception {
		// arrange
		classToTest = new JobStep(mockReader, mockProcessor, mockWriter);
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
	public void testCommit() throws Exception {
		// arrange
		classToTest = new JobStep(mockReader, mockWriter);
		initMocks();
		// act
		classToTest.execute(2);
		// assert
		verify(classToTest.reader, Mockito.times(2)).read();
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}
}

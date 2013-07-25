package com.dfremont.simplebatch;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class JobStepTest {

	JobStep<Object> classToTest;
	ItemReader<Object> mockReader = Mockito.mock(ItemReader.class);
	ItemProcessor<Object> mockProcessor = Mockito.mock(ItemProcessor.class);
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
			new JobStep<Object>(null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep<Object>(mockReader, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep<Object>(null, mockWriter);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		new JobStep<Object>(mockReader, mockWriter);
	}

	@Test
	public void test_nonregression_on_constructor_3_args_wants_reader_and_writer_and_perhaps_processor()
			throws Exception {
		try {
			new JobStep<Object>(null, null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep<Object>(mockReader, null, null);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		try {
			new JobStep<Object>(null, null, mockWriter);
			fail("expected error");
		} catch (IllegalArgumentException e) {
		}
		new JobStep<Object>(mockReader, null, mockWriter);
		new JobStep<Object>(mockReader, mockProcessor, mockWriter);
	}

	@Test
	public void testExecuteWithoutProcessor() {
		// arrange
		classToTest = new JobStep<Object>(mockReader, mockWriter);
		Object mockedItem1 = new Object();
		List<Object> mockedItems = Arrays.asList(mockedItem1, mockedItem1);
		when(mockReader.read()) //
				.thenReturn(mockedItems);
		// act
		classToTest.execute();
		// assert
		verify(classToTest.reader, Mockito.times(1)).read();
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}

	@Test
	public void testExecuteTransformByProcessor() {
		// arrange
		classToTest = new JobStep<Object>(mockReader, mockProcessor, mockWriter);
		Object mockedItem1 = new Object();
		Object mockedItem1processed = new Object();
		List<Object> mockedItems = Arrays.asList(mockedItem1, mockedItem1);
		when(mockReader.read()) //
				.thenReturn(mockedItems);
		when(mockProcessor.process(mockedItem1)) //
				.thenReturn(mockedItem1processed);
		// act
		classToTest.execute();
		// assert
		verify(classToTest.reader, Mockito.times(1)).read();
		verify(classToTest.processor, Mockito.times(2)).process(mockedItem1);
		verify(classToTest.writer, Mockito.times(1)).write(Mockito.anyList());
		// TODO arg capture
	}
}

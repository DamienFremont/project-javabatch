package com.dfremont.simplebatch.core;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

public class JobTest {

	@Test
	public void testExecute() throws Exception {
		// arrange
		JobStep<?, ?> stepMock1 = Mockito.mock(JobStep.class);
		JobStep<?, ?> stepMock2 = Mockito.mock(JobStep.class);
		@SuppressWarnings("unchecked")
		Job classToTest = new Job(Arrays.asList(stepMock1, stepMock2));
		// act
		classToTest.execute();
		// assert
		Mockito.verify(stepMock1).execute();
		Mockito.verify(stepMock2).execute();
	}

}

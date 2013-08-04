package com.dfremont.simplebatch.core;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class BatchProcessTest {

	@Test
	public void testExecute() throws Exception {
		// arrange
		Step<?, ?> stepMock1 = Mockito.mock(Step.class);
		Step<?, ?> stepMock2 = Mockito.mock(Step.class);
		BatchProcess classToTest = new BatchProcess(Arrays.asList(stepMock1, stepMock2));
		// act
		classToTest.execute();
		// assert
		Mockito.verify(stepMock1).execute();
		Mockito.verify(stepMock2).execute();
	}

}

package com.dfremont.simplebatch;

import java.io.File;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import com.dfremont.simplebatch.infra.file.FileItemReader;
import com.dfremont.simplebatch.infra.file.FileItemWriter;

public class ITFileToFileTest {

	@Test
	public void test_should_copy_file() throws Exception {
		// arrange
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (new File("out.txt").exists()) {
			new File("out.txt").delete();
		}
		Assertions.assertThat(new File(path + "/in.txt")).exists();
		Assertions.assertThat(new File("out.txt")).doesNotExist();
		// act
		BatchRunnerFluent
				.createBatch()
				.setReader(
						new FileItemReader<String>(new File(path + "/in.txt"))) //
				.setWriter(new FileItemWriter<String>(new File("out.txt"))) //
				.run();
		// assert
		Assertions.assertThat(new File("out.txt")).exists()
				.hasContentEqualTo(new File(path + "/out_expected.txt"));
		// clean
		if (new File("out.txt").exists()) {
			new File("out.txt").delete();
		}
	}
}

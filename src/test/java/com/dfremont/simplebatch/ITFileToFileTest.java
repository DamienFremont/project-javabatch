package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

import com.dfremont.simplebatch.core.BatchReport;
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
		assertThat(new File(path + "/in.txt")).exists();
		assertThat(new File("out.txt")).doesNotExist();
		// act
		BatchReport report = BatchRunnerFluent.createBatch() //
				.setReader( //
						new FileItemReader<String>(new File(path + "/in.txt"))) //
				.setWriter( //
						new FileItemWriter<String>(new File("out.txt"))) //
				.run().getReport();
		// assert
		assertThat(report).isNotNull();
		assertThat(report.getStatus())//
				.isEqualTo("TERMINATED");
		assertThat(report.getExecution()) //
				.contains("linecount=4") //
				.contains("linesWritten=3") //
				.contains("file=out.txt");
		assertThat(new File("out.txt")).exists().hasContentEqualTo(
				new File(path + "/out_expected.txt"));
		// clean
		if (new File("out.txt").exists()) {
			new File("out.txt").delete();
		}
	}
}

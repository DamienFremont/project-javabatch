package com.dfremont.simplebatch;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dfremont.simplebatch.core.ExecutionReport;
import com.dfremont.simplebatch.core.ItemProcessor;
import com.dfremont.simplebatch.infra.file.FileItemReader;
import com.dfremont.simplebatch.infra.file.FileItemWriter;
import com.dfremont.simplebatch.infra.file.FileLineMapper;

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
		ExecutionReport report = BatchRunnerFluent
				.createBatch()
				//
				.setReader( //
						new FileItemReader<List<String>>(//
								new File(path + "/in.txt"), //
								new FileLineMapper<List<String>>("{0},{1},{2]",
										",")))
				.setWriter( //
						new FileItemWriter<List<String>>(//
								new File("out.txt"), //
								new FileLineMapper<List<String>>("{0},{1},{2}",
										","))) //
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

	@Test
	public void test_should_copy_file_2_of_3_columns() throws Exception {
		// arrange
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (new File("out_2of3.html").exists()) {
			new File("out_2of3.html").delete();
		}
		assertThat(new File(path + "/in.txt")).exists();
		assertThat(new File("out_2of3.html")).doesNotExist();
		// act
		ExecutionReport report = BatchRunnerFluent
				.createBatch()
				.setReader( //
						new FileItemReader<List<String>>(//
								new File(path + "/in.txt"), //
								new FileLineMapper<List<String>>(
										"<li>{0}: {1}</li>", ",")))
				.setProcessor(new ItemProcessor<List<String>, List<String>>() {
					public List<String> process(List<String> item)
							throws Exception {
						List<String> transfItem = new ArrayList<String>();
						for (String col : item) {
							transfItem.add(col.replaceAll("a", "A"));
						}
						return transfItem;
					}
				})
				.setWriter( //
						new FileItemWriter<List<String>>(//
								new File("out_2of3.html"), //
								new FileLineMapper<List<String>>(
										"<li>{0}: {1}</li>", ","))) //
				.run().getReport();
		// assert
		assertThat(report).isNotNull();
		assertThat(report.getStatus())//
				.isEqualTo("TERMINATED");
		assertThat(report.getExecution()) //
				.contains("linecount=4") //
				.contains("linesWritten=3") //
				.contains("file=out_2of3.html");
		assertThat(new File("out_2of3.html")).exists().hasContentEqualTo(
				new File(path + "/out_2of3_expected.html"));
		// clean
		if (new File("out_2of3.html").exists()) {
			new File("out_2of3.html").delete();
		}
	}
}

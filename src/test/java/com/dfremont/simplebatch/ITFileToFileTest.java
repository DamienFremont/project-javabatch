package com.dfremont.simplebatch;

import static com.dfremont.simplebatch.BatchRunnerFluent.createBatch;
import static com.dfremont.simplebatch.BatchRunnerFluent.createStep;
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
	static final String PATH = ITFileToFileTest.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath()
			+ "/file";

	@Test
	public void test_should_copy_file() throws Exception {
		// arrange
		if (new File("out.txt").exists())
			new File("out.txt").delete();
		assertThat(new File(PATH + "/in.txt")).exists();
		assertThat(new File("out.txt")).doesNotExist();
		// act
		ExecutionReport report = BatchRunnerFluent
				.createBatch()
				.setReader( //
						new FileItemReader<List<String>>(//
								new File(PATH + "/in.txt"), //
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
				.contains("linecount=3") //
				.contains("linesWritten=3") //
				.contains("file=out.txt");
		assertThat(new File("out.txt")).exists().hasContentEqualTo(
				new File(PATH + "/out_expected.txt"));
		// clean
		if (new File("out.txt").exists())
			new File("out.txt").delete();
	}

	@Test
	public void test_should_copy_file_2_of_3_columns() throws Exception {
		// arrange
		if (new File("out_2of3.html").exists())
			new File("out_2of3.html").delete();
		assertThat(new File(PATH + "/in.txt")).exists();
		assertThat(new File("out_2of3.html")).doesNotExist();
		// act
		ExecutionReport report = BatchRunnerFluent
				.createBatch()
				.setReader( //
						new FileItemReader<List<String>>(//
								new File(PATH + "/in.txt"), //
								new FileLineMapper<List<String>>(
										"<li>{0}: {1}</li>", ",")))
				.setProcessor(new ItemProcessor<List<String>, List<String>>() {
					public List<String> process(List<String> item)
							throws Exception {
						List<String> transfItem = new ArrayList<String>();
						for (String col : item)
							transfItem.add(col.replaceAll("a", "A"));
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
				.contains("linecount=3") //
				.contains("linesWritten=3") //
				.contains("file=out_2of3.html");
		assertThat(new File("out_2of3.html")).exists().hasContentEqualTo(
				new File(PATH + "/out_2of3_expected.html"));
		// clean
		if (new File("out_2of3.html").exists())
			new File("out_2of3.html").delete();
	}

	@Test
	public void test_should_filter_then_transform_file() throws Exception {
		// arrange
		if (new File("out_filtered.csv").exists())
			new File("out_filtered.csv").delete();
		assertThat(new File(PATH + "/in.txt")).exists();
		assertThat(new File("out_filtered.csv")).doesNotExist();
		// act
		FileLineMapper<List<String>> commonMapper = new FileLineMapper<List<String>>(
				"{0},{1},{2}", ",");
		ExecutionReport report = createBatch()
				.addStep(
						createStep("filter")
								.setReader( //
										new FileItemReader<List<String>>(
												new File(PATH + "/in.txt"), //
												commonMapper))
								.setProcessor( //
										new ItemProcessor<List<String>, List<String>>() {
											public List<String> process(
													List<String> item)
													throws Exception {
												for (String col : item)
													if (col.contains("2"))
														return item;
												return null;
											}
										})
								.setWriter( //
										new FileItemWriter<List<String>>(
												new File("out_filtered.txt"), //
												commonMapper)) //
				)
				.addStep(
						createStep("copy").setReader( //
								new FileItemReader<List<String>>(new File(
										"out_filtered.txt"), //
										commonMapper)).setWriter( //
								new FileItemWriter<List<String>>(new File(
										"out_filtered.csv"), //
										new FileLineMapper<List<String>>(
												"{0};{1};{2}", ","))) //
				).run().getReport();
		// assert
		assertThat(report.getStatus())//
				.isEqualTo("TERMINATED");
		assertThat(report.getExecution()) //
				.contains("linecount=3") // FIXME count must be = 3
				.contains("linesWritten=2") //
				.contains("file=out_filtered.csv");
		assertThat(new File("out_filtered.csv")).exists().hasContentEqualTo(
				new File(PATH + "/out_filtered_expected.csv"));
		// clean
		if (new File("out_filtered.txt").exists())
			new File("out_filtered.txt").delete();
		if (new File("out_filtered.csv").exists())
			new File("out_filtered.csv").delete();
	}
}

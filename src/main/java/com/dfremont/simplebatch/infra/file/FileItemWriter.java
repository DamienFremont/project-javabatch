package com.dfremont.simplebatch.infra.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.dfremont.simplebatch.core.ItemWriter;

public class FileItemWriter<ITEM> implements ItemWriter<ITEM> {
	static final String DEFAULT_LINE_SEPARATOR = System
			.getProperty("line.separator");
	File file;
	int linesWritten = 0;
	String header;
	String footer;
	FileLineMapper<ITEM> mapper;

	public FileItemWriter(File fileToWrite, FileLineMapper<ITEM> mapper)
			throws IOException {
		file = fileToWrite;
		if (!file.exists()) {
			file.createNewFile();
		}
		this.mapper = mapper;
	}

	public void write(List<? extends ITEM> items) throws Exception {
		// transform
		StringBuilder lines = new StringBuilder();
		if (header != null)
			lines.append(header);
		int lineCount = 0;
		for (ITEM item : items) {
			lines.append(mapper.map(item) + DEFAULT_LINE_SEPARATOR);
			lineCount++;
		}
		if (footer != null)
			lines.append(footer);
		// write
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(lines.toString());
		linesWritten += lineCount;
		bw.close();
	}

	public String getExecution() {
		return String.format("file=%s, linesWritten=%d", file, linesWritten);
	}

}

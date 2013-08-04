package com.dfremont.simplebatch.infra.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.dfremont.simplebatch.core.ItemWriter;

public class FileItemWriter<ITEM> implements ItemWriter<ITEM> {
	private static final String DEFAULT_LINE_SEPARATOR = System
			.getProperty("line.separator");

	private File file;

	private int linesWritten = 0;

	public FileItemWriter(File fileToWrite) throws IOException {
		file = fileToWrite;
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public void write(List<? extends ITEM> items) throws Exception {
		// transform
		StringBuilder lines = new StringBuilder();
		int lineCount = 0;
		for (ITEM item : items) {
			lines.append(item + DEFAULT_LINE_SEPARATOR);
			lineCount++;
		}
		// write
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(lines.toString());
		linesWritten += lineCount;
		bw.close();
	}

}

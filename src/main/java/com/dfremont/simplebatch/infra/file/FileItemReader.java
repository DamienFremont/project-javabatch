package com.dfremont.simplebatch.infra.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.dfremont.simplebatch.core.Executable;
import com.dfremont.simplebatch.core.ItemReader;

public class FileItemReader<ITEM> implements ItemReader<ITEM>, Executable {
	BufferedReader reader;
	int lineCount = 0;

	public FileItemReader(File file, FileLineMapper<ITEM> mapper)
			throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(file));
		this.mapper = mapper;
	}

	FileLineMapper<ITEM> mapper;

	public ITEM read() throws Exception {
		String line = reader.readLine(); // TODO gerer encoding
		if (line == null) {
			reader.close();
			return null;
		}
		lineCount++;
		return mapper.map(line); // TODO object mapping
	}

	public String getExecution() {
		return String.format("linecount=%s", lineCount);
	}
}

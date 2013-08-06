package com.dfremont.simplebatch.infra.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.dfremont.simplebatch.core.ItemReader;

public class FileItemReader<ITEM> implements ItemReader<ITEM> {
	BufferedReader reader;
	int lineCount = 0;

	public FileItemReader(File file) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(file));
	}

	public ITEM read() throws Exception {
		String line = reader.readLine(); // TODO gerer encoding
		lineCount++;
		if (line == null) {
			reader.close();
			return null;
		}
		return (ITEM) line; // TODO object mapping
	}
}

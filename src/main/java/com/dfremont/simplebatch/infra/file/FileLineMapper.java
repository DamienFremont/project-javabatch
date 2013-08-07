package com.dfremont.simplebatch.infra.file;

import java.util.Arrays;
import java.util.List;

public class FileLineMapper<ITEM> {
	String separator;
	String pattern;

	public FileLineMapper(String pattern, String separator) {
		this.pattern = pattern;
		this.separator = separator;
	}

	ITEM map(String line) {
		return (ITEM) Arrays.asList(line.split(separator));
	}

	String map(ITEM item) {
		String result = pattern;
		for (int i = 0; i < ((List<String>) item).size(); i++) {
			result = result.replaceAll("\\{" + i + "\\}", //
					(((List<String>) item).get(i) == null) ? ""
							: ((List<String>) item).get(i));
		}
		return result;
	}
}

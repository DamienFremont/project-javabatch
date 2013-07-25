package com.dfremont.simplebatch;

import java.util.ArrayList;
import java.util.List;

public class JobStep<ITEM extends Object> {

	private static final String MSG_CONSTPRIVATE = "Use public constructors instead";
	private static final String MSG_CONST = "Reader and Processor are Mandatory";
	ItemReader<ITEM> reader;
	ItemProcessor<ITEM> processor;
	ItemWriter<ITEM> writer;

	// TODO properties?
	int confCommitInterval;
	int confSkipLimit;
	int skipError;

	@SuppressWarnings("unused")
	private JobStep() {
		throw new IllegalArgumentException(MSG_CONSTPRIVATE);
	}

	public JobStep(ItemReader<ITEM> newReader, ItemWriter<ITEM> newWriter) {
		if (!(newReader != null && newWriter != null))
			throw new IllegalArgumentException(MSG_CONST);
		this.reader = newReader;
		this.writer = newWriter;
	}

	public JobStep(ItemReader<ITEM> newReader,
			ItemProcessor<ITEM> newProcessor, ItemWriter<ITEM> newWriter) {
		this(newReader, newWriter);
		this.processor = newProcessor;
	}

	void execute() {
		execute(confCommitInterval);
		// TODO execute(1); on chunck
	}

	private void execute(int commitInterval) {
		List<ITEM> chunck = reader.read(); // TODO chunck extends List?
		if (processor != null) {
			List<ITEM> processedChunck = new ArrayList<ITEM>();
			for (ITEM item : chunck) {
				processedChunck.add(processor.process(item));
			}
			chunck = processedChunck;
		}
		writer.write(chunck);
	}
}

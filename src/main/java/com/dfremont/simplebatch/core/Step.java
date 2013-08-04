package com.dfremont.simplebatch.core;

import java.util.ArrayList;
import java.util.List;

public class Step<READITEM, WRITEITEM> {

	private static final String MSG_CONSTPRIVATE = "Use public constructors instead";
	private static final String MSG_CONST = "Reader and Processor are Mandatory";
	String name;

	ItemReader<READITEM> reader;
	ItemProcessor<READITEM, WRITEITEM> processor;
	ItemWriter<WRITEITEM> writer;

	// TODO properties?
	// TODO int confCommitInterval;
	// TODO int confSkipLimit;
	// TODO int skipError;

	@SuppressWarnings("unused")
	private Step() {
		throw new IllegalArgumentException(MSG_CONSTPRIVATE);
	}

	public Step(ItemReader<READITEM> newReader,
			ItemWriter<WRITEITEM> newWriter) {
		if (!(newReader != null && newWriter != null))
			throw new IllegalArgumentException(MSG_CONST);
		this.reader = newReader;
		this.writer = newWriter;
	}

	public Step(ItemReader<READITEM> newReader,
			ItemProcessor<READITEM, WRITEITEM> newProcessor,
			ItemWriter<WRITEITEM> newWriter) {
		this(newReader, newWriter);
		this.processor = newProcessor;
	}

	public void execute() throws Exception {
		execute(0);
		// TODO execute(1); on chunck
	}

	@SuppressWarnings("unchecked")
	void execute(int commitInterval) throws Exception {
		READITEM item = reader.read(); 
		// TODO batch by commit value
		List<WRITEITEM> chunck = new ArrayList<WRITEITEM>(); // TODO chunck extends List?
		int index = 0;
		while (item != null && (index < (commitInterval-1) | commitInterval == 0)) {
			if (processor != null) {
				chunck.add(processor.process(item));
			} else {
				chunck.add((WRITEITEM) item);
			}
			index++;
			item = reader.read();
		}
		writer.write(chunck);
	}
}

package com.dfremont.simplebatch.core;

public interface ItemProcessor<ITEMREAD, ITEMTOWRITE> extends Executable {
	ITEMTOWRITE process(ITEMREAD item) throws Exception;
}

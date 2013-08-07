package com.dfremont.simplebatch.core;

public interface ItemProcessor<ITEMREAD, ITEMTOWRITE> {
	ITEMTOWRITE process(ITEMREAD item) throws Exception;

	String getExectution();
}

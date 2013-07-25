package com.dfremont.simplebatch;

public interface ItemProcessor<ITEM extends Object> {
	ITEM process(ITEM itemToProcess);
}

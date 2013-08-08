package com.dfremont.simplebatch.core;

public interface ItemReader<ITEM> extends Executable {
	ITEM read() throws Exception;
}
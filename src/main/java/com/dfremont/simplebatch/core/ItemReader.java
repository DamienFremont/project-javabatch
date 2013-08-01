package com.dfremont.simplebatch.core;


public interface ItemReader<ITEM> {
	ITEM read() throws Exception;
}
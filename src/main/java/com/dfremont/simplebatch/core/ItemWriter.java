package com.dfremont.simplebatch.core;

import java.util.List;

public interface ItemWriter<ITEM> {
	void write(List<? extends ITEM> items) throws Exception;

	String getExectution();
}

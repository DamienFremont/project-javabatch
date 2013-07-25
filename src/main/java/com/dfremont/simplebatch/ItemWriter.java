package com.dfremont.simplebatch;

import java.util.List;

public interface ItemWriter<ITEM extends Object> {
	void write(List<ITEM> itemsArrayToWrite);
}

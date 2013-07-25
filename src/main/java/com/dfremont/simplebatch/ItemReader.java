package com.dfremont.simplebatch;

import java.util.List;

public interface ItemReader<ITEM extends Object> {
	List<ITEM> read();
}

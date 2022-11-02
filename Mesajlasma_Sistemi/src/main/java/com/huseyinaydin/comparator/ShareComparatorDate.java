package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Message;
import com.huseyinaydin.model.Share;

public class ShareComparatorDate implements Comparator<Share> {

	@Override
	public int compare(Share a, Share b) {
		return b.getShare_Date().compareTo(a.getShare_Date());
	}

}

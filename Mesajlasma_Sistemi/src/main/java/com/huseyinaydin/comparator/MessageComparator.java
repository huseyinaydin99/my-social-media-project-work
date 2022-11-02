package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Message;

public class MessageComparator implements Comparator<Message> {

	@Override
	public int compare(Message a, Message b) {
		Long long1 = a.getMessage_Id();
		Long long2 = b.getMessage_Id();
		return long1.compareTo(long2);
	}

}

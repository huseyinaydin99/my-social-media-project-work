package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Message;

public class MessageBooleanComparator implements Comparator<Message> {

	@Override
	public int compare(Message arg0, Message arg1) {
		Boolean boolean1 = arg0.isGorulmeDurumu2();
		Boolean boolean2 = arg1.isGorulmeDurumu2();
		return boolean1.compareTo(boolean2);
	}

}

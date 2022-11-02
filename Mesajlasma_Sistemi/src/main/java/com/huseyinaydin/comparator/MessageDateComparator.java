package com.huseyinaydin.comparator;

import java.util.Comparator;
import java.util.Date;

import com.huseyinaydin.model.Message;

public class MessageDateComparator implements Comparator<Message> {

	@Override
	public int compare(Message arg0, Message arg1) {
		return arg1.getSonMesajTarihi().compareTo(arg0.getSonMesajTarihi());
	}

	

}

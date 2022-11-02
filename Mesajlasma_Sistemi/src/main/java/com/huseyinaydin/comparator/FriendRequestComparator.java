package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.FriendRequest;

public class FriendRequestComparator implements Comparator<FriendRequest> {

	@Override
	public int compare(FriendRequest arg0, FriendRequest arg1) {
		Long a = arg1.getFriend_Request_Id();
		Long b = arg0.getFriend_Request_Id();
		return a.compareTo(b);
	}

}

package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.FriendRequest;
import com.huseyinaydin.model.Person;

public interface FriendDAO {
	public void sendFriendRequest(long personId);
	public byte isFriend(long personId);
	public List<FriendRequest> getMyFriendRequest(long personId);
	public long getMyFriendRequestCount(long personId);
	public void setMyFriendRequestViewStatu(long personId);
	public void setMyFriendRequestStatu(long personId);
	public List<Person> getMyFriends();
}

package com.huseyinaydin.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.FriendDAO;
import com.huseyinaydin.model.FriendRequest;
import com.huseyinaydin.model.Person;

@Service
@ManagedBean(name = "friendService")
@RequestScoped
public class FriendServiceImpl implements FriendService {

	@Autowired
	@Qualifier("friendDAO")
	private FriendDAO friendDAO;
	
	@Transactional
	@Override
	public void sendFriendRequest(long personId) {
		this.friendDAO.sendFriendRequest(personId);
	}

	public FriendDAO getFriendDAO() {
		return friendDAO;
	}

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	@Transactional
	@Override
	public byte isFriend(long personId) {
		return this.friendDAO.isFriend(personId);
	}

	@Transactional
	@Override
	public List<FriendRequest> getMyFriendRequest(long personId) {
		return this.friendDAO.getMyFriendRequest(personId);
	}

	@Transactional
	@Override
	public long getMyFriendRequestCount(long personId) {
		return this.friendDAO.getMyFriendRequestCount(personId);
	}

	@Transactional
	@Override
	public void setMyFriendRequestViewStatu(long personId) {
		this.friendDAO.setMyFriendRequestViewStatu(personId);
	}

	@Transactional
	@Override
	public void setMyFriendRequestStatu(long personId) {
		this.friendDAO.setMyFriendRequestStatu(personId);
		
	}

	@Transactional
	@Override
	public List<Person> getMyFriends() {
		return friendDAO.getMyFriends();
	}

	
}

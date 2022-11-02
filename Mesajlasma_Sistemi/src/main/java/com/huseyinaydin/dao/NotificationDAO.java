package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

public interface NotificationDAO {
	public long getNotificationCount();
	public List<Likex> getUnseenLikes();
	public List<ShareCommit> getUnseenShareCommit();
	public List<ShareCR> getUnseenShareCR();
	public void gorulmeDurumuUpdate();
	
	public List<Likex> getSeenLikes();
	public List<ShareCommit> getSeenShareCommit();
	public List<ShareCR> getSeenShareCR();
	
	public List<ShareCR> getFriendShareUnSeenCommit();
	public List<ShareCR> getFriendShareSeenCommit();
}

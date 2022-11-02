package com.huseyinaydin.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.NotificationDAO;
import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

@Service
@ManagedBean(name = "notificationService")
@RequestScoped
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	@Qualifier("notificationDAO")
	private NotificationDAO notificationDAO;
	
	@Transactional
	@Override
	public long getNotificationCount() {
		return this.notificationDAO.getNotificationCount();
	}

	public NotificationDAO getNotificationDAO() {
		return notificationDAO;
	}

	public void setNotificationDAO(NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}

	@Transactional
	@Override
	public List<Likex> getUnseenLikes() {
		return this.notificationDAO.getUnseenLikes();
	}

	@Transactional
	@Override
	public List<ShareCommit> getUnseenShareCommit() {
		return this.notificationDAO.getUnseenShareCommit();
	}

	@Transactional
	@Override
	public List<ShareCR> getUnseenShareCR() {
		return this.notificationDAO.getUnseenShareCR();
	}

	@Transactional
	@Override
	public void gorulmeDurumuUpdate() {
		this.notificationDAO.gorulmeDurumuUpdate();
	}

	@Transactional
	@Override
	public List<Likex> getSeenLikes() {
		return this.notificationDAO.getSeenLikes();
	}

	@Transactional
	@Override
	public List<ShareCommit> getSeenShareCommit() {
		return this.notificationDAO.getSeenShareCommit();
	}

	@Transactional
	@Override
	public List<ShareCR> getSeenShareCR() {
		return this.notificationDAO.getSeenShareCR();
	}

	@Transactional
	@Override
	public List<ShareCR> getFriendShareUnSeenCommit() {
		return this.notificationDAO.getFriendShareUnSeenCommit();
	}

	@Transactional
	@Override
	public List<ShareCR> getFriendShareSeenCommit() {
		return this.notificationDAO.getFriendShareSeenCommit();
	}

}

package com.huseyinaydin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.ShareDAO;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareImage;
@Service
@ManagedBean(name = "shareService")
@SessionScoped
public class ShareServiceImpl implements ShareService {

	@Autowired
	@Qualifier("shareDAO")
	private ShareDAO shareDAO;
	
	public List<Share> otherShareList;
	
	public ShareServiceImpl() {
		super();
	}

	@Transactional
	@Override
	public void saveShare(String shareText, Part uploadedFiles) throws ServletException, IOException {
		this.shareDAO.saveShare(shareText, uploadedFiles);
	}

	public ShareDAO getShareDAO() {
		return shareDAO;
	}

	public void setShareDAO(ShareDAO shareDAO) {
		this.shareDAO = shareDAO;
	}

	@Transactional
	@Override
	public List<Share> getMyShare() {
		return this.shareDAO.getMyShare();
	}

	@Transactional
	@Override
	public List<Share> getOtherShare() {
		this.otherShareList = this.shareDAO.getOtherShare();
		return this.otherShareList;
	}

	@Transactional
	@Override
	public Share getShare(long shareId) {
		return this.shareDAO.getShare(shareId);
	}

	@Transactional
	@Override
	public Share getShare() {
		return this.shareDAO.getShare();
	}

	@Transactional
	@Override
	public List<Share> getShares() {
		return this.shareDAO.getShares();
	}

	@Transactional
	@Override
	public List<Share> getProfileShare() {
		return this.shareDAO.getProfileShare();
	}

	@Transactional
	@Override
	public List<ShareImage> getShareImageList() {
		return shareDAO.getShareImageList();
	}

	@Override
	public List<Share> getOtherShareList() {
		return this.otherShareList;
	}

}

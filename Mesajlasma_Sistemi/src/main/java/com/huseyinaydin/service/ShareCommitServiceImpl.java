package com.huseyinaydin.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.ShareCommitDAO;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCommit;

@Service
@ManagedBean(name = "shareCommitService")
@RequestScoped
public class ShareCommitServiceImpl implements ShareCommitService {

	@Autowired
	@Qualifier("shareCommitDAO")
	private ShareCommitDAO shareCommitDAO;
	
	@Transactional
	@Override
	public void saveShareCommit(ShareCommit shareCommit,Share share) {
		this.shareCommitDAO.saveShareCommit(shareCommit,share);
	}

	public ShareCommitDAO getShareCommitDAO() {
		return shareCommitDAO;
	}

	public void setShareCommitDAO(ShareCommitDAO shareCommitDAO) {
		this.shareCommitDAO = shareCommitDAO;
	}

	@Transactional
	@Override
	public List<ShareCommit> getShareCommit(Share share) {
		return this.shareCommitDAO.getShareCommit(share);
	}

	@Transactional
	@Override
	public List<ShareCommit> getOtherShareCommit(Share share) {
		return this.shareCommitDAO.getOtherShareCommit(share);
	}

	@Transactional
	@Override
	public void gorulmeDurumuUpdateShareCommit(ShareCommit shareCommit) {
		this.shareCommitDAO.gorulmeDurumuUpdateShareCommit(shareCommit);
	}

	@Transactional
	@Override
	public void deleteShareCommit(ShareCommit shareCommit) {
		this.shareCommitDAO.deleteShareCommit(shareCommit);
	}

	@Transactional
	@Override
	public void updateShareCommitDuzenleDurum(boolean durum, ShareCommit shareCommit) {
		this.shareCommitDAO.updateShareCommitDuzenleDurum(durum, shareCommit);
	}

	@Transactional
	@Override
	public void updateShareCommitDuzenleDurum(boolean durum, ShareCommit shareCommit, String text) {
		this.shareCommitDAO.updateShareCommitDuzenleDurum(durum, shareCommit, text);
	}

}

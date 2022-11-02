package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

public interface ShareCommitDAO {
	public void saveShareCommit(ShareCommit shareCommit,Share share);
	public List<ShareCommit> getShareCommit(Share share);
	public List<ShareCommit> getOtherShareCommit(Share share);
	public void gorulmeDurumuUpdateShareCommit(ShareCommit shareCommit);
	public void deleteShareCommit(ShareCommit shareCommit);
	
	public void updateShareCommitDuzenleDurum(boolean durum,ShareCommit shareCommit);
	public void updateShareCommitDuzenleDurum(boolean durum,ShareCommit shareCommit,String text);
}

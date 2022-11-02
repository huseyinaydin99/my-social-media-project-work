package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

public interface ShareCRDAO {
	public void saveShareCR(ShareCR shareCR,Share share, ShareCommit shareCommit);
	public List<ShareCR> getShareCR(Share share,ShareCommit shareCommit);
	public List<ShareCR> getOtherShareCR(Share share,ShareCommit shareCommit);
	public void gorulmeDurumuUpdateShareCR(ShareCR shareCR);
	public void deleteShareCR(ShareCR shareCR);
	public void updateShareCR(ShareCR shareCR);
	public void updateShareCRDuzenleDurum(boolean durum,ShareCR shareCR);
	public void updateShareCRDuzenleDurum(boolean durum,ShareCR shareCR,String text);
}

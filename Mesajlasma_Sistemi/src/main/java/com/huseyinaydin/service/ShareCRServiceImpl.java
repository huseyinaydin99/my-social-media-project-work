package com.huseyinaydin.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.ShareCRDAO;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

@Service
@ManagedBean(name = "shareCRService")
@RequestScoped
public class ShareCRServiceImpl implements ShareCRService {

	@Autowired
	@Qualifier("shareCRDAO")
	private ShareCRDAO shareCRDAO;

	@Transactional
	@Override
	public void saveShareCR(ShareCR shareCR, Share share, ShareCommit shareCommit,String text) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\Hüseyin\\Desktop\\asdf.txt")));
			bufferedWriter.write(text);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("hasan " + e.getMessage());
			e.printStackTrace();
		}
		shareCR.setShareCRText(text);
		this.shareCRDAO.saveShareCR(shareCR, share, shareCommit);
	}

	public ShareCRDAO getShareCRDAO() {
		return shareCRDAO;
	}

	public void setShareCRDAO(ShareCRDAO shareCRDAO) {
		this.shareCRDAO = shareCRDAO;
	}

	@Transactional
	@Override
	public List<ShareCR> getShareCR(Share share, ShareCommit shareCommit) {
		return this.shareCRDAO.getShareCR(share, shareCommit);
	}

	@Transactional
	@Override
	public List<ShareCR> getOtherShareCR(Share share, ShareCommit shareCommit) {
		return this.shareCRDAO.getOtherShareCR(share, shareCommit);
	}

	@Transactional
	@Override
	public void gorulmeDurumuUpdateShareCR(ShareCR shareCR) {
		this.shareCRDAO.gorulmeDurumuUpdateShareCR(shareCR);

	}

	@Transactional
	@Override
	public void deleteShareCR(ShareCR shareCR) {
		this.shareCRDAO.deleteShareCR(shareCR);
	}

	@Transactional
	@Override
	public void updateShareCRDuzenleDurum(boolean durum, ShareCR shareCR) {
		this.shareCRDAO.updateShareCRDuzenleDurum(durum, shareCR);
	}

	@Transactional
	@Override
	public void updateShareCRDuzenleDurum(boolean durum, ShareCR shareCR, String text) {
		this.shareCRDAO.updateShareCRDuzenleDurum(durum, shareCR, text);
	}

}

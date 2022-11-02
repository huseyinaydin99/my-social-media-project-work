package com.huseyinaydin.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "shareCR")
@RequestScoped
public class ShareCR implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long shareCR_Id;
	private String shareCRText;
	private Person person;
	private Share share;
	private ShareCommit shareCommit;
	private boolean gorulme_Durumu;
	private boolean duzenleDurum;
	
	public ShareCR() {
		super();
	}
	
	public long getShareCR_Id() {
		return shareCR_Id;
	}
	public void setShareCR_Id(long shareCR_Id) {
		this.shareCR_Id = shareCR_Id;
	}
	
	public String getShareCRText() {
		return shareCRText;
	}
	public void setShareCRText(String shareCRText) {
		this.shareCRText = shareCRText;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public ShareCommit getShareCommit() {
		return shareCommit;
	}
	public void setShareCommit(ShareCommit shareCommit) {
		this.shareCommit = shareCommit;
	}
	public Share getShare() {
		return share;
	}
	public void setShare(Share share) {
		this.share = share;
	}
	public boolean isGorulme_Durumu() {
		return gorulme_Durumu;
	}
	public void setGorulme_Durumu(boolean gorulme_Durumu) {
		this.gorulme_Durumu = gorulme_Durumu;
	}

	public boolean isDuzenleDurum() {
		return duzenleDurum;
	}

	public void setDuzenleDurum(boolean duzenleDurum) {
		this.duzenleDurum = duzenleDurum;
	}
	
}

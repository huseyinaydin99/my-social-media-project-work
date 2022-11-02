package com.huseyinaydin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "shareCommit")
@RequestScoped
public class ShareCommit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long shareCommit_Id;
	private String shareCommitText;
	private Person person;
	private Share share;
	private Set<ShareCR> shareCRs = new HashSet<>();
	private boolean gorulme_Durumu;
	private boolean duzenleDurum;
	
	public long getShareCommit_Id() {
		return shareCommit_Id;
	}
	public void setShareCommit_Id(long shareCommit_Id) {
		this.shareCommit_Id = shareCommit_Id;
	}
	public String getShareCommitText() {
		return shareCommitText;
	}
	public void setShareCommitText(String shareCommitText) {
		this.shareCommitText = shareCommitText;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Share getShare() {
		return share;
	}
	public void setShare(Share share) {
		this.share = share;
	}
	public Set<ShareCR> getShareCRs() {
		return shareCRs;
	}
	public void setShareCRs(Set<ShareCR> shareCRs) {
		this.shareCRs = shareCRs;
	}
	
	public String idVer(){
		return String.valueOf("_su" + this.getShareCommit_Id());
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

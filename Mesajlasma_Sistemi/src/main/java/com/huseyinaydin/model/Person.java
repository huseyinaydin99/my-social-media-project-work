package com.huseyinaydin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "person")
@RequestScoped
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long person_Id;
	private String person_Adi;
	private String person_Soyadi;
	private String person_Username;
	private String person_Password;
	private Date person_DTar;
	private String person_ProfilePhoto;
	private String person_BannerPhoto;
	private String islem_Durumu;

	private Set<Message> messages = new HashSet<>();
	private Set<Commit> commits = new HashSet<>();
	private Set<FriendRequest> friendRequests = new HashSet<>();
	private Set<Share> shares = new HashSet<>();
	private Set<ShareCommit> shareCommits = new HashSet<>();
	private Set<ShareCR> shareCRs = new HashSet<>();
	private Set<Likex> likes = new HashSet<>();

	public long getPerson_Id() {
		return person_Id;
	}

	public void setPerson_Id(long person_Id) {
		this.person_Id = person_Id;
	}

	public String getPerson_Adi() {
		return person_Adi;
	}

	public void setPerson_Adi(String person_Adi) {
		this.person_Adi = person_Adi;
	}

	public String getPerson_Soyadi() {
		return person_Soyadi;
	}

	public void setPerson_Soyadi(String person_Soyadi) {
		this.person_Soyadi = person_Soyadi;
	}

	public Date getPerson_DTar() {
		return person_DTar;
	}

	public void setPerson_DTar(Date person_DTar) {
		this.person_DTar = person_DTar;
	}

	public String getIslem_Durumu() {
		return islem_Durumu;
	}

	public void setIslem_Durumu(String islem_Durumu) {
		this.islem_Durumu = islem_Durumu;
	}

	public String getPerson_Username() {
		return person_Username;
	}

	public void setPerson_Username(String person_Username) {
		this.person_Username = person_Username;
	}

	public String getPerson_Password() {
		return person_Password;
	}

	public void setPerson_Password(String person_Password) {
		this.person_Password = person_Password;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Commit> getCommits() {
		return commits;
	}

	public void setCommits(Set<Commit> commits) {
		this.commits = commits;
	}

	public String getPerson_ProfilePhoto() {
		return person_ProfilePhoto;
	}

	public void setPerson_ProfilePhoto(String person_ProfilePhoto) {
		this.person_ProfilePhoto = person_ProfilePhoto;
	}

	public String getPerson_BannerPhoto() {
		return person_BannerPhoto;
	}

	public void setPerson_BannerPhoto(String person_BannerPhoto) {
		this.person_BannerPhoto = person_BannerPhoto;
	}

	public String profilFotosunuVer() {
		try {
			if (person_ProfilePhoto.length() > 0)
				return "/resources/images/" + person_ProfilePhoto;
			else
				return "/resources/images/default_profile.png";
		} catch (NullPointerException e) {
			return "/resources/images/default_profile.png";
		}
	}

	public String bannerFotosunuVer() {
		try {
			if (person_BannerPhoto.length() > 0)
				return "/resources/images/" + person_BannerPhoto;
			else
				return "/resources/images/default_banner.png";
		} catch (NullPointerException e) {
			return "/resources/images/default_banner.png";
		}
	}

	public Set<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(Set<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}

	public Set<Share> getShares() {
		return shares;
	}

	public void setShares(Set<Share> shares) {
		this.shares = shares;
	}

	public Set<ShareCommit> getShareCommits() {
		return shareCommits;
	}

	public void setShareCommits(Set<ShareCommit> shareCommits) {
		this.shareCommits = shareCommits;
	}

	public Set<ShareCR> getShareCRs() {
		return shareCRs;
	}

	public void setShareCRs(Set<ShareCR> shareCRs) {
		this.shareCRs = shareCRs;
	}

	public Set<Likex> getLikes() {
		return likes;
	}

	public void setLikes(Set<Likex> likes) {
		this.likes = likes;
	}

	

	
}

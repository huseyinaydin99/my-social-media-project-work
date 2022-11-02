package com.huseyinaydin.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.huseyinaydin.comparator.LikeComparator;
import com.huseyinaydin.comparator.ShareCommitComparator;

@ManagedBean(name = "share")
@RequestScoped
public class Share implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long share_Id;
	private Person person;
	private String share_Images;
	private String share_Text;
	private Date share_Date;
	private Set<ShareImage> images = new HashSet<>();
	private Set<Likex> likes = new HashSet<>();
	private List<Likex> likexsList = new ArrayList<>();
	
	private Set<ShareCommit> shareCommits = new HashSet<>();
	private List<ShareCommit> shareCommitsList = new ArrayList<>();
	private List<ShareCR> shareCRList = new ArrayList<>();

	public Share(long share_Id, Person person, String share_Images, String share_Text, Date share_Date,
			Set<ShareImage> images) {
		super();
		this.share_Id = share_Id;
		this.person = person;
		this.share_Images = share_Images;
		this.share_Text = share_Text;
		this.share_Date = share_Date;
		this.images = images;
	}

	public Share() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getShare_Id() {
		return share_Id;
	}

	public void setShare_Id(long share_Id) {
		this.share_Id = share_Id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getShare_Images() {
		return share_Images;
	}

	public void setShare_Images(String share_Images) {
		this.share_Images = share_Images;
	}

	public String getShare_Text() {
		return share_Text;
	}

	public void setShare_Text(String share_Text) {
		this.share_Text = share_Text;
	}

	public Date getShare_Date() {
		return share_Date;
	}

	public void setShare_Date(Date share_Date) {
		this.share_Date = share_Date;
	}

	public Set<ShareImage> getImages() {
		return images;
	}

	public void setImages(Set<ShareImage> images) {
		this.images = images;
	}
	
	public String idVer(){
		return String.valueOf("_hu"+this.getShare_Id());
	}

	public Set<Likex> getLikes() {
		return likes;
	}

	public void setLikes(Set<Likex> likes) {
		this.likes = likes;
		this.likexsList.addAll(likes);
		Collections.sort(likexsList,new LikeComparator());
	}

	public Set<ShareCommit> getShareCommits() {
		return shareCommits;
	}

	public void setShareCommits(Set<ShareCommit> shareCommits) {
		this.shareCommits = shareCommits;
		this.shareCommitsList.addAll(shareCommits);
		Collections.sort(shareCommitsList,new ShareCommitComparator());
	}

	public List<Likex> getLikexsList() {
		return likexsList;
	}

	public void setLikexsList(List<Likex> likexsList) {
		this.likexsList = likexsList;
	}

	public List<ShareCommit> getShareCommitsList() {
		return shareCommitsList;
	}

	public void setShareCommitsList(List<ShareCommit> shareCommitsList) {
		this.shareCommitsList = shareCommitsList;
	}

	public List<ShareCR> getShareCRList() {
		return shareCRList;
	}

	public void setShareCRList(List<ShareCR> shareCRList) {
		this.shareCRList = shareCRList;
	}
	
}

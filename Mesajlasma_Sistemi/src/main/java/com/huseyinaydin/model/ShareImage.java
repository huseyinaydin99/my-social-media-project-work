package com.huseyinaydin.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "shareImage")
@RequestScoped
public class ShareImage {
	private long shareImage_Id;
	private Share share;
	private String image;
	public ShareImage(long shareImage_Id, Share share, String image) {
		super();
		this.shareImage_Id = shareImage_Id;
		this.share = share;
		this.image = image;
	}
	public ShareImage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getShareImage_Id() {
		return shareImage_Id;
	}
	public void setShareImage_Id(long shareImage_Id) {
		this.shareImage_Id = shareImage_Id;
	}
	public Share getShare() {
		return share;
	}
	public void setShare(Share share) {
		this.share = share;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}

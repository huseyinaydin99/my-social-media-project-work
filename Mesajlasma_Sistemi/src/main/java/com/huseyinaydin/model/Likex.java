package com.huseyinaydin.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

@ManagedBean(name = "like")
@RequestScoped
public class Likex implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long like_Id;
	private Share share;
	private Person person;
	private boolean gorulme_Durumu;
	
	public long getLike_Id() {
		return like_Id;
	}
	public void setLike_Id(long like_Id) {
		this.like_Id = like_Id;
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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
}

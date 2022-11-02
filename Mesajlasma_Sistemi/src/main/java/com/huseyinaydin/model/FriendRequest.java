package com.huseyinaydin.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "friendRequest")
@RequestScoped
public class FriendRequest {
	private long friend_Request_Id;
	private Person person;
	private long to;
	private boolean gorulme_Durumu;
	private boolean kabul_Edilme_Durumu;
	public long getFriend_Request_Id() {
		return friend_Request_Id;
	}
	public void setFriend_Request_Id(long friend_Request_Id) {
		this.friend_Request_Id = friend_Request_Id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public long getTo() {
		return to;
	}
	public void setTo(long to) {
		this.to = to;
	}
	public boolean isGorulme_Durumu() {
		return gorulme_Durumu;
	}
	public void setGorulme_Durumu(boolean gorulme_Durumu) {
		this.gorulme_Durumu = gorulme_Durumu;
	}
	public boolean isKabul_Edilme_Durumu() {
		return kabul_Edilme_Durumu;
	}
	public void setKabul_Edilme_Durumu(boolean kabul_Edilme_Durumu) {
		this.kabul_Edilme_Durumu = kabul_Edilme_Durumu;
	}
	public String isteginKabulEdilmeDurumu(){
		if(isKabul_Edilme_Durumu())
			return "Kabul Edildi";
		else
			return "Kabul Edilmedi";
	}
	
	
}

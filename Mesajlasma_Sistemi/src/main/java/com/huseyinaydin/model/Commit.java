package com.huseyinaydin.model;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "commit")
@RequestScoped
public class Commit {
	private long commit_Id;
	private Message message;
	private Person person;
	private long to;
	private String commit_Body;
	private boolean gorulmeDurumu;
	private String commit_Photo;
	private Date commitKayitTarihi;
	private Date commitKayitSaati;
	
	public Commit() {
		super();
		this.commitKayitTarihi = Calendar.getInstance().getTime();
		this.commitKayitSaati = Calendar.getInstance().getTime();
	}
	public long getCommit_Id() {
		return commit_Id;
	}
	public void setCommit_Id(long commit_Id) {
		this.commit_Id = commit_Id;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
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
	public String getCommit_Body() {
		return commit_Body;
	}
	public void setCommit_Body(String commit_Body) {
		this.commit_Body = commit_Body;
	}
	public boolean isGorulmeDurumu() {
		return gorulmeDurumu;
	}
	public void setGorulmeDurumu(boolean gorulmeDurumu) {
		this.gorulmeDurumu = gorulmeDurumu;
	}
	public String getCommit_Photo() {
		return commit_Photo;
	}
	public void setCommit_Photo(String commit_Photo) {
		this.commit_Photo = commit_Photo;
	}
	public Date getCommitKayitTarihi() {
		return commitKayitTarihi;
	}
	public void setCommitKayitTarihi(Date commitKayitTarihi) {
		this.commitKayitTarihi = commitKayitTarihi;
	}
	public Date getCommitKayitSaati() {
		return commitKayitSaati;
	}
	public void setCommitKayitSaati(Date commitKayitSaati) {
		this.commitKayitSaati = commitKayitSaati;
	}
	
}

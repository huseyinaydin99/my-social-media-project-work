package com.huseyinaydin.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "message")
@RequestScoped
public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long message_Id;
	private Person person;
	private long to;
	private String message_Body;
	private String islemDurumu;
	private long kimeGidecek;
	private boolean gorulmeDurumu;
	private boolean gorulmeDurumu2 = false;
	private long listIndex;
	private String message_Photo;
	private Date messageKayitTarihi;
	private Date messageKayitSaati;
	private Date sonMesajTarihi;
	private String message_Body2;
	
	private Set<Commit> commits = new HashSet<>();
	
	public Message() {
		super();
		this.gorulmeDurumu2 = false;
		this.messageKayitTarihi = Calendar.getInstance().getTime();
		this.messageKayitSaati = Calendar.getInstance().getTime();
	}
	public long getMessage_Id() {
		return message_Id;
	}
	public void setMessage_Id(long message_Id) {
		this.message_Id = message_Id;
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
	public String getMessage_Body() {
		return message_Body;
	}
	public void setMessage_Body(String message_Body) {
		this.message_Body = message_Body;
	}
	public Set<Commit> getCommits() {
		return commits;
	}
	public void setCommits(Set<Commit> commits) {
		this.commits = commits;
	}
	public String getIslemDurumu() {
		return islemDurumu;
	}
	public void setIslemDurumu(String islemDurumu) {
		this.islemDurumu = islemDurumu;
	}
	public long getKimeGidecek() {
		return kimeGidecek;
	}
	public void setKimeGidecek(long kimeGidecek) {
		this.kimeGidecek = kimeGidecek;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isGorulmeDurumu() {
		return gorulmeDurumu;
	}
	public void setGorulmeDurumu(boolean gorulmeDurumu) {
		this.gorulmeDurumu = gorulmeDurumu;
	}
	public long getListIndex() {
		return listIndex;
	}
	public void setListIndex(long listIndex) {
		this.listIndex = listIndex;
	}
	public String getMessage_Photo() {
		return message_Photo;
	}
	public void setMessage_Photo(String message_Photo) {
		this.message_Photo = message_Photo;
	}
	public boolean isGorulmeDurumu2() {
		return gorulmeDurumu2;
	}
	public void setGorulmeDurumu2(boolean gorulmeDurumu2) {
		this.gorulmeDurumu2 = gorulmeDurumu2;
	}
	public Date getMessageKayitTarihi() {
		return messageKayitTarihi;
	}
	public void setMessageKayitTarihi(Date messageKayitTarihi) {
		this.messageKayitTarihi = messageKayitTarihi;
	}
	public Date getMessageKayitSaati() {
		return messageKayitSaati;
	}
	public void setMessageKayitSaati(Date messageKayitSaati) {
		this.messageKayitSaati = messageKayitSaati;
	}
	public Date getSonMesajTarihi() {
		return sonMesajTarihi;
	}
	public void setSonMesajTarihi(Date sonMesajTarihi) {
		this.sonMesajTarihi = sonMesajTarihi;
	}
	public String getMessage_Body2() {
		return message_Body2;
	}
	public void setMessage_Body2(String message_Body2) {
		this.message_Body2 = message_Body2;
	}
	public String getGorulmeDurumuCanim() {
		if(this.isGorulmeDurumu2()) {
			return "Görüldü";
		}
		else
			return "Henüz görmediniz";
	}
}

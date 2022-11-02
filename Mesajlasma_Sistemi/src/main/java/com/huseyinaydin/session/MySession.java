package com.huseyinaydin.session;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.huseyinaydin.model.Person;

@ManagedBean(name = "mySession")
@SessionScoped
public class MySession {
	private Person person;
	private long kim = 0;

	public Person getPerson() {
		if(person==null){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void init(){
		System.out.println("session başladı");
	}
	
	public void destroy(){
		person = null;
		getPerson();
		System.out.println("session bitti");
	}
	
	public void git(long id){
		if(person.getPerson_Id() == id) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("profilim.jsf");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("profil.jsf?profileID="+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void git2(long id){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("mesajlasma.jsf?profileID="+id);
			kim = id;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void git3(long id){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("paylasim.jsf?shareId="+id);
			//kim = id;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getKim() {
		return kim;
	}

	public void setKim(long kim) {
		this.kim = kim;
	}
	
	
}

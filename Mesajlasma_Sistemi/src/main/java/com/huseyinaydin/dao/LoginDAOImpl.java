package com.huseyinaydin.dao;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.session.MySession;

@Repository
public class LoginDAOImpl implements LoginDAO {

	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	@Qualifier("mySession")
	private MySession mySession;
	@Override
	public void loginIn(Person person) {
		this.session = this.sessionFactory.getCurrentSession();
		Query query = this.session.createQuery("FROM Person as p where p.person_Username=:kadi and p.person_Password=:pass");
		System.out.println("kadi ve şifre");
		System.out.println(person.getPerson_Username());
		System.out.println(person.getPerson_Password());
		query.setParameter("kadi", person.getPerson_Username());
		query.setParameter("pass", person.getPerson_Password());
		List<Person> persons = query.list();
		if(persons.size()>0){
			mySession.setPerson(persons.get(0));
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("anasayfa.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			person.setIslem_Durumu("Kullanıcı adı veya şifre yanlış.!");
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public MySession getMySession() {
		return mySession;
	}
	public void setMySession(MySession mySession) {
		this.mySession = mySession;
	}

}

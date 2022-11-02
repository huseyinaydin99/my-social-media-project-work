package com.huseyinaydin.dao;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.session.MySession;

@Repository
public class PersonDAOImpl implements PersonDAO {
	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	@Qualifier("mySession")
	private MySession mySession;
	
	@Override
	public void personKaydet(Person person) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.save(person);
		person.setIslem_Durumu("Kayıt yapıldı!");
		mySession.setPerson(person);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			facesContext.getExternalContext().redirect("anasayfa.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MySession getMySession() {
		return mySession;
	}

	public void setMySession(MySession mySession) {
		this.mySession = mySession;
	}

	@Override
	public List<Person> personKaydiniAl(long id) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("person_Id", id));
		List<Person> persons = criteria.list();
		return persons;
	}

	@Override
	public Person personuAl(Person person) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("person_Id", person.getPerson_Id()));
		List<Person> persons = criteria.list();
		Person person2 = null;
		if(persons.size()>0){
			person2 = persons.get(0);
		}
		return person2;
	}

	@Override
	public Person personuAl(long id) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("person_Id", id));
		List<Person> persons = criteria.list();
		Person person2 = null;
		if(persons.size()>0){
			person2 = persons.get(0);
		}
		return person2;
	}

}

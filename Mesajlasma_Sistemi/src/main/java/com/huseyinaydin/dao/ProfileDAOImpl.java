package com.huseyinaydin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.session.MySession;

@Repository
public class ProfileDAOImpl implements ProfileDAO {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	
	@Override
	public void saveProfilePhoto(Person person) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.update(person);
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

	@Transactional
	@Override
	public String getProfilePhoto() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("person_Id", mySession.getPerson().getPerson_Id()));
		List<Person> persons = criteria.list();
		return persons.get(0).getPerson_ProfilePhoto();
	}


	@Override
	public void saveBannerPhoto(Person person) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.update(person);
	}


	@Override
	public String getBannerPhoto() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("person_Id", mySession.getPerson().getPerson_Id()));
		List<Person> persons = criteria.list();
		return persons.get(0).getPerson_BannerPhoto();
	}
	
	
}

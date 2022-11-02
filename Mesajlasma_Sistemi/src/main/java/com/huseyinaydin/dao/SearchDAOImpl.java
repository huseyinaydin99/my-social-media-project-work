package com.huseyinaydin.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.SearchModel;

@Repository
public class SearchDAOImpl implements SearchDAO {
	
	private SessionFactory sessionFactory;
	private Session session;
	
	@Override
	public List<Person> searchPerson(SearchModel searchModel) {
		this.session = this.sessionFactory.getCurrentSession();
		Query query = this.session.createQuery("FROM Person as p where p.person_Adi LIKE:adi or p.person_Soyadi LIKE:adi");
		query.setParameter("adi", searchModel.getWord()+"%");
		List<Person> persons = query.list();
		return persons;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

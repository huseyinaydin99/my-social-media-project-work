package com.huseyinaydin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.session.MySession;

@Repository
public class LikeDAOImpl implements LikeDAO {
	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;
	
	@Override
	public void saveLike(Share share) {
		this.session = this.sessionFactory.getCurrentSession();
		Likex like = new Likex();
		like.setGorulme_Durumu(false);
		like.setShare(share);
		like.setPerson(this.mySession.getPerson());
		Criteria criteria = this.session.createCriteria(Likex.class);
		criteria.add(Restrictions.and(Restrictions.eq("share", share),Restrictions.eq("person", this.mySession.getPerson())));
		List<Likex> likexs = criteria.list();
		if(!likexs.isEmpty()){
			Likex likex = likexs.get(0);
			return;
		}
		else
			this.session.save(like);
	}
	
	@Override
	public void gorulmeDurumuUpdateLike(Likex likex) {
		this.session = this.sessionFactory.getCurrentSession();
		likex.setGorulme_Durumu(true);
		this.session.update(likex);
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

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public MySession getMySession() {
		return mySession;
	}

	public void setMySession(MySession mySession) {
		this.mySession = mySession;
	}

	@Override
	public List<Likex> getShareLikes(Share share) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Likex.class);
		criteria.add(Restrictions.eq("share", share));
		List<Likex> likexs = null;
		try {
			likexs = criteria.list();
		}
		catch(TransientObjectException e) {
			
		}
		return likexs;
	}

}

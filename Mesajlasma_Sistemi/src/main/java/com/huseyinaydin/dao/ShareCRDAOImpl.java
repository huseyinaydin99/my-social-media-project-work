package com.huseyinaydin.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.comparator.ShareCRComparator;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.session.MySession;

@Repository
public class ShareCRDAOImpl implements ShareCRDAO {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	@Override
	public void saveShareCR(ShareCR shareCR, Share share, ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCR.setPerson(this.mySession.getPerson());
		shareCR.setShare(share);
		shareCR.setShareCommit(shareCommit);
		this.session.save(shareCR);
	}

	@Override
	public List<ShareCR> getShareCR(Share share, ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(ShareCR.class);
		criteria.add(Restrictions.and(Restrictions.eq("share", share),
				Restrictions.eq("shareCommit", shareCommit)));
		List<ShareCR> shareCRs = criteria.list();
		Collections.sort(shareCRs,new ShareCRComparator());
		return shareCRs;
	}
	
	@Override
	public List<ShareCR> getOtherShareCR(Share share, ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(ShareCR.class);
		criteria.add(Restrictions.and(Restrictions.eq("share", share),
				Restrictions.eq("shareCommit", shareCommit)));
		List<ShareCR> shareCRs = criteria.list();
		Collections.sort(shareCRs,new ShareCRComparator());
		return shareCRs;
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
	public void gorulmeDurumuUpdateShareCR(ShareCR shareCR) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCR.setGorulme_Durumu(true);
		this.session.update(shareCR);
	}

	@Override
	public void deleteShareCR(ShareCR shareCR) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.delete(shareCR);
	}

	@Override
	public void updateShareCR(ShareCR shareCR) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.update(shareCR);
	}

	@Override
	public void updateShareCRDuzenleDurum(boolean durum, ShareCR shareCR) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCR.setDuzenleDurum(durum);
		this.session.update(shareCR);
	}

	@Override
	public void updateShareCRDuzenleDurum(boolean durum, ShareCR shareCR, String text) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCR.setDuzenleDurum(durum);
		shareCR.setShareCRText(text);
		this.session.update(shareCR);
	}
}

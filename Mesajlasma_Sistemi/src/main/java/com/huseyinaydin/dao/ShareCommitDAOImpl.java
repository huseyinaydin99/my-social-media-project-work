package com.huseyinaydin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.comparator.ShareCommitComparator;
import com.huseyinaydin.comparator.ShareCommitResponseComparator;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ProfileService;
import com.huseyinaydin.session.MySession;

@Repository
public class ShareCommitDAOImpl implements ShareCommitDAO {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	@Autowired
	@Qualifier("profileService")
	private ProfileService profileService;

	@Override
	public void saveShareCommit(ShareCommit shareCommit, Share share) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCommit.setShare(share);
		shareCommit.setPerson(this.mySession.getPerson());
		this.session.save(shareCommit);
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

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	@Override
	public List<ShareCommit> getShareCommit(Share share) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(ShareCommit.class);
		criteria.add(Restrictions.and(Restrictions.eq("share", share)));
		List<ShareCommit> shareCommits = criteria.list();
		Collections.sort(shareCommits, new ShareCommitComparator());
		for (int i = 0; i < shareCommits.size(); i++) {
			ShareCommit shareCommit = shareCommits.get(i);
			List<ShareCR> shareCRs = new ArrayList<>();
			Set<ShareCR> set = shareCommit.getShareCRs();
			shareCRs.addAll(set);
			Collections.sort(shareCRs, new ShareCommitResponseComparator());
		}
		return shareCommits;
	}

	@Override
	public List<ShareCommit> getOtherShareCommit(Share share) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(ShareCommit.class);
		criteria.add(Restrictions.and(Restrictions.eq("share", share)));
		List<ShareCommit> shareCommits = criteria.list();
		Collections.sort(shareCommits, new ShareCommitComparator());
		for (int i = 0; i < shareCommits.size(); i++) {
			ShareCommit shareCommit = shareCommits.get(i);
			List<ShareCR> shareCRs = new ArrayList<>();
			Set<ShareCR> set = shareCommit.getShareCRs();
			shareCRs.addAll(set);
			Collections.sort(shareCRs, new ShareCommitResponseComparator());
		}
		return shareCommits;
	}

	@Override
	public void gorulmeDurumuUpdateShareCommit(ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCommit.setGorulme_Durumu(true);
		this.session.update(shareCommit);
	}

	@Override
	public void deleteShareCommit(ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.delete(shareCommit);
	}

	@Override
	public void updateShareCommitDuzenleDurum(boolean durum, ShareCommit shareCommit) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCommit.setDuzenleDurum(durum);
		this.session.update(shareCommit);
	}

	@Override
	public void updateShareCommitDuzenleDurum(boolean durum, ShareCommit shareCommit, String text) {
		this.session = this.sessionFactory.getCurrentSession();
		shareCommit.setDuzenleDurum(durum);
		shareCommit.setShareCommitText(text);
		this.session.update(shareCommit);
	}

}

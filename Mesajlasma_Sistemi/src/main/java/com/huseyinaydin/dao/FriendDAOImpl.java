package com.huseyinaydin.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.comparator.FriendRequestComparator;
import com.huseyinaydin.model.FriendRequest;
import com.huseyinaydin.model.Person;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ProfileService;
import com.huseyinaydin.session.MySession;

@Repository
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	@Qualifier("hibernate5AnnotatedSessionFactory")
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
	public void sendFriendRequest(long personId) {
		this.session = this.sessionFactory.getCurrentSession();
		Person person = mySession.getPerson();
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setPerson(person);
		friendRequest.setTo(personService.personuAl(personId).getPerson_Id());
		this.session.save(friendRequest);
		System.err.println("Friend DAO istek gönderildi!");
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
	public byte isFriend(long personId) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(FriendRequest.class);
		criteria.add(Restrictions.and(Restrictions.eq("person", this.mySession.getPerson()),
				Restrictions.eq("to", personId)));
		List<FriendRequest> friendRequests = criteria.list();
		byte returns = 100;
		if (!friendRequests.isEmpty()) {
			FriendRequest friendRequest = friendRequests.get(0);
			if (!friendRequest.isKabul_Edilme_Durumu()) {
				returns = 1;// istek gönderildi ancak kabul edilmedi
			} else if (friendRequest.isKabul_Edilme_Durumu()) {
				returns = 2;// istek gönderildi ve kabul edildi
			}
		} else {
			Criteria criteria2 = this.session.createCriteria(FriendRequest.class);
			criteria2.add(Restrictions.and(Restrictions.eq("person", this.personService.personuAl(personId)),
					Restrictions.eq("to", this.mySession.getPerson().getPerson_Id())));
			List<FriendRequest> friendRequests2 = criteria2.list();
			if (!friendRequests2.isEmpty()) {
				FriendRequest friendRequest = friendRequests2.get(0);
				if (!friendRequest.isKabul_Edilme_Durumu()) {
					returns = 3;// istek alındı ancak kabul edilmedi
				} else if (friendRequest.isKabul_Edilme_Durumu()) {
					returns = 2;// istek alındı ve kabul edildi
				}
			} else
				returns = 0;// istek gönderilmedi
		}
		return returns;
	}

	@Override
	public List<FriendRequest> getMyFriendRequest(long personId) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(FriendRequest.class);
		criteria.add(Restrictions.eq("to", personId));
		List<FriendRequest> friendRequests = criteria.list();
		List<FriendRequest> gorulmeyenler = new ArrayList<>();
		List<FriendRequest> gorunenler = new ArrayList<>();
		List<FriendRequest> total = new ArrayList<>();
		for (int i = 0; i < friendRequests.size(); i++) {
			FriendRequest friendRequest = friendRequests.get(i);
			if (!friendRequest.isGorulme_Durumu()) {
				gorulmeyenler.add(friendRequest);
			} else if (friendRequest.isGorulme_Durumu()) {
				gorunenler.add(friendRequest);
			}
		}
		for (int i = 0; i < gorulmeyenler.size(); i++) {
			total.add(gorulmeyenler.get(i));
		}
		for (int i = 0; i < gorunenler.size(); i++) {
			total.add(gorunenler.get(i));
		}
		Collections.sort(total, new FriendRequestComparator());
		return total;
	}

	@Override
	public long getMyFriendRequestCount(long personId) {
		List<FriendRequest> friendRequests = getMyFriendRequest(personId);
		List<FriendRequest> gorulmeyenler = new ArrayList<>();
		for (int i = 0; i < friendRequests.size(); i++) {
			FriendRequest friendRequest = friendRequests.get(i);
			if (!friendRequest.isGorulme_Durumu()) {
				gorulmeyenler.add(friendRequest);
			}
		}
		return gorulmeyenler.size();
	}

	@Transactional
	@Override
	public void setMyFriendRequestViewStatu(long personId) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(FriendRequest.class);
		criteria.add(Restrictions.eq("to", personId));
		List<FriendRequest> friendRequests = criteria.list();
		for (int i = 0; i < friendRequests.size(); i++) {
			FriendRequest friendRequest = friendRequests.get(i);
			friendRequest.setGorulme_Durumu(true);
			this.session.update(friendRequest);
		}
	}

	@Override
	public void setMyFriendRequestStatu(long personId) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(FriendRequest.class);
		criteria.add(Restrictions.and(Restrictions.eq("person", this.personService.personuAl(personId)),
				Restrictions.eq("to", this.mySession.getPerson().getPerson_Id())));
		List<FriendRequest> friendRequests = criteria.list();
		if (!friendRequests.isEmpty()) {
			FriendRequest friendRequest = friendRequests.get(0);
			friendRequest.setKabul_Edilme_Durumu(true);
			this.session.update(friendRequest);
		} else {
			System.err.println("Liste boş");
		}
	}

	@Override
	public List<Person> getMyFriends() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(FriendRequest.class);
		criteria.add(Restrictions.eq("to", this.mySession.getPerson().getPerson_Id()));//alıcısı biz olan
		
		Criteria criteria2 = this.session.createCriteria(FriendRequest.class);
		criteria2.add(Restrictions.eq("person", mySession.getPerson()));//göndericisi biz olan
		
		List<FriendRequest> friendRequests = criteria.list();
		List<FriendRequest> friendRequests2 = criteria2.list();
		
		List<Person> friendList = new ArrayList<>();
		if (!friendRequests.isEmpty() || !friendRequests2.isEmpty()) {
			for(int i = 0; i < friendRequests.size(); i++) {
				FriendRequest friendRequest = friendRequests.get(i);
				if(friendRequest.isKabul_Edilme_Durumu())
				friendList.add(personService.personuAl(friendRequest.getPerson().getPerson_Id()));
			}
			for(int i = 0; i < friendRequests2.size(); i++) {
				FriendRequest friendRequest = friendRequests2.get(i);
				if(friendRequest.isKabul_Edilme_Durumu())
				friendList.add(personService.personuAl(friendRequest.getTo()));
			}
		} else {
			System.err.println("Liste boş");
		}
		return friendList;
	}

}

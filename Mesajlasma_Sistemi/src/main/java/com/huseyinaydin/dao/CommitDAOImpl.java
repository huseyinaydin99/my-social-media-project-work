package com.huseyinaydin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.model.Commit;
import com.huseyinaydin.session.MySession;

@Repository
public class CommitDAOImpl implements CommitDAO {

	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	@Qualifier("mySession")
	private MySession mySession;
	
	@Transactional
	@Override
	public void sendMessage(Commit commit) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.save(commit);
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
	public void deleteCommit(Commit commit) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.delete(commit);
	}
}

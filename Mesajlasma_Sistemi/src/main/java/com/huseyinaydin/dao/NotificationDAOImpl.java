package com.huseyinaydin.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import com.huseyinaydin.comparator.LikeComparator;
import com.huseyinaydin.comparator.ShareCRComparator;
import com.huseyinaydin.comparator.ShareCommitComparator;
import com.huseyinaydin.comparator.ShareComparator;
import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;
import com.huseyinaydin.service.FriendService;
import com.huseyinaydin.service.LikeService;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ShareCRService;
import com.huseyinaydin.service.ShareCommitService;
import com.huseyinaydin.session.MySession;

@Repository
public class NotificationDAOImpl implements NotificationDAO {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;
	private long notificationIndex = 0;
	
	@Autowired
	@Qualifier("likeService")
	private LikeService likeService;
	
	@Autowired
	@Qualifier("shareCRService")
	private ShareCRService shareCRService;
	
	@Autowired
	@Qualifier("shareCommitService")
	private ShareCommitService shareCommitService;
	
	@Autowired
	@Qualifier("friendService")
	private FriendService friendService;

	@Override
	public long getNotificationCount() {
		notificationIndex = 0;
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<Likex> likexs = share.getLikes();
			List<Likex> likexsList = new ArrayList<>();
			likexsList.addAll(likexs);
			Collections.sort(likexsList, new LikeComparator());
			for (int j = 0; j < likexsList.size(); j++) {
				Likex likex = likexsList.get(j);
				if (!likex.isGorulme_Durumu()
						&& likex.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
					notificationIndex++;
				}
			}
			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {
					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (!shareCommit.isGorulme_Durumu()
							&& shareCommit.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
						boolean bizeAitDurum = false;
						for (int x = 0; x < shareCRsList.size(); x++) {
							ShareCR shareCR = shareCRsList.get(x);
							if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
								bizeAitDurum = true;
								break;
							}
						}
						if (bizeAitDurum == true) {

						} else {
							notificationIndex++;
						}
					}

					if (!shareCRsList.isEmpty()) {
						ShareCR shareCRi = shareCRsList.get(shareCRsList.size() - 1);
						if (shareCRi.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
							int kalanIndex = 0;
							for (int x = 0; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
									kalanIndex = x;
								}
							}
							for (int x = kalanIndex; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id() != this.mySession
										.getPerson().getPerson_Id()) {
									notificationIndex++;
								}
							}
						}
					}
				}
			}
		}
		prosedur1();
		return notificationIndex;
	}
	
	public void prosedur1() {
		List<ShareCR> returnedShareCRList = new ArrayList<>();
		
		List<Person> myFriends = friendService.getMyFriends();
		for(int i = 0; i < myFriends.size(); i++) {
			Person person = this.personService.personuAl(myFriends.get(i).getPerson_Id());
			Set<Share> shares = person.getShares();
			List<Share> sharesList = new ArrayList<>();
			sharesList.addAll(shares);
			Collections.sort(sharesList,new ShareComparator());
			for(int j = 0; j < sharesList.size(); j++) {
				Share share = sharesList.get(j);
				Set<ShareCommit> shareCommits = share.getShareCommits();
				List<ShareCommit> shareCommitsList = new ArrayList<>();
				shareCommitsList.addAll(shareCommits);
				Collections.sort(shareCommitsList, new ShareCommitComparator());
				for(int f = 0; f < shareCommitsList.size(); f++) {
					ShareCommit shareCommit = shareCommitsList.get(f);
					if(shareCommit.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
						Set<ShareCR> shareCRs = shareCommit.getShareCRs();
						List<ShareCR> shareCRsList = new ArrayList<>();
						shareCRsList.addAll(shareCRs);
						Collections.sort(shareCRsList, new ShareCRComparator());
						for(int h = 0; h < shareCRsList.size(); h++) {
							ShareCR shareCR = shareCRsList.get(h);
							if(!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id()!=this.mySession.getPerson().getPerson_Id()) {
								this.notificationIndex++;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<Likex> getUnseenLikes() {
		this.session = this.sessionFactory.getCurrentSession();
		List<Likex> likexss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<Likex> likexs = share.getLikes();
			List<Likex> likexsList = new ArrayList<>();
			likexsList.addAll(likexs);
			Collections.sort(likexsList, new LikeComparator());
			for (int j = 0; j < likexsList.size(); j++) {
				Likex likex = likexsList.get(j);
				if (!likex.isGorulme_Durumu()
						&& likex.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
					// notificationIndex++;
					likexss.add(likex);
				}
			}
		}
		return likexss;
	}

	@Override
	public List<ShareCommit> getUnseenShareCommit() {
		this.session = this.sessionFactory.getCurrentSession();
		List<ShareCommit> shareCommitss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);

			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {

					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (!shareCommit.isGorulme_Durumu()
							&& shareCommit.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
						/*
						 * if (!shareCRsList.isEmpty()) { } else { //
						 * notificationIndex++; shareCommitss.add(shareCommit);
						 * }
						 */
						boolean bizeAitDurum = false;
						for (int x = 0; x < shareCRsList.size(); x++) {
							ShareCR shareCR = shareCRsList.get(x);
							if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
								bizeAitDurum = true;
								break;
							}
						}
						if (bizeAitDurum == true) {

						} else {
							shareCommitss.add(shareCommit);
						}
					}
				}
			}
		}

		return shareCommitss;
	}

	@Override
	public List<ShareCR> getUnseenShareCR() {
		this.session = this.sessionFactory.getCurrentSession();
		List<ShareCR> shareCRss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);

			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {

					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (!shareCRsList.isEmpty()) {
						ShareCR shareCRi = shareCRsList.get(shareCRsList.size() - 1);
						if (shareCRi.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
							int kalanIndex = 0;
							for (int x = 0; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
									kalanIndex = x;
								}
							}
							for (int x = kalanIndex; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id() != this.mySession
										.getPerson().getPerson_Id()) {
									// notificationIndex++;
									shareCRss.add(shareCR);
								}
							}
						}
					}
				}
			}
		}

		return shareCRss;
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

	public LikeService getLikeService() {
		return likeService;
	}

	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}

	public ShareCRService getShareCRService() {
		return shareCRService;
	}

	public void setShareCRService(ShareCRService shareCRService) {
		this.shareCRService = shareCRService;
	}

	public ShareCommitService getShareCommitService() {
		return shareCommitService;
	}

	public void setShareCommitService(ShareCommitService shareCommitService) {
		this.shareCommitService = shareCommitService;
	}

	@Override
	public void gorulmeDurumuUpdate() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<Likex> likexs = share.getLikes();
			List<Likex> likexsList = new ArrayList<>();
			likexsList.addAll(likexs);
			Collections.sort(likexsList, new LikeComparator());
			for (int j = 0; j < likexsList.size(); j++) {
				Likex likex = likexsList.get(j);
				if (!likex.isGorulme_Durumu()
						&& likex.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
					//notificationIndex++;
					likeService.gorulmeDurumuUpdateLike(likex);
				}
			}
			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {
					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (!shareCommit.isGorulme_Durumu()
							&& shareCommit.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
						/*
						 * if (!shareCRsList.isEmpty()) { } else
						 * notificationIndex++;
						 */
						boolean bizeAitDurum = false;
						for (int x = 0; x < shareCRsList.size(); x++) {
							ShareCR shareCR = shareCRsList.get(x);
							if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
								bizeAitDurum = true;
								break;
							}
						}
						if (bizeAitDurum == true) {

						} else {
							//notificationIndex++;
							this.shareCommitService.gorulmeDurumuUpdateShareCommit(shareCommit);
						}
					}

					if (!shareCRsList.isEmpty()) {
						ShareCR shareCRi = shareCRsList.get(shareCRsList.size() - 1);
						if (shareCRi.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
							int kalanIndex = 0;
							for (int x = 0; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
									kalanIndex = x;
								}
							}
							for (int x = kalanIndex; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id() != this.mySession
										.getPerson().getPerson_Id()) {
									//notificationIndex++;
									this.shareCRService.gorulmeDurumuUpdateShareCR(shareCR);
								}
							}
						}
					}
				}
			}
		}
		prosedur3();
	}
	
	public void prosedur3() {
		List<ShareCR> returnedShareCRList = new ArrayList<>();
		
		List<Person> myFriends = friendService.getMyFriends();
		for(int i = 0; i < myFriends.size(); i++) {
			Person person = this.personService.personuAl(myFriends.get(i).getPerson_Id());
			Set<Share> shares = person.getShares();
			List<Share> sharesList = new ArrayList<>();
			sharesList.addAll(shares);
			Collections.sort(sharesList,new ShareComparator());
			for(int j = 0; j < sharesList.size(); j++) {
				Share share = sharesList.get(j);
				Set<ShareCommit> shareCommits = share.getShareCommits();
				List<ShareCommit> shareCommitsList = new ArrayList<>();
				shareCommitsList.addAll(shareCommits);
				Collections.sort(shareCommitsList, new ShareCommitComparator());
				for(int f = 0; f < shareCommitsList.size(); f++) {
					ShareCommit shareCommit = shareCommitsList.get(f);
					if(shareCommit.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
						Set<ShareCR> shareCRs = shareCommit.getShareCRs();
						List<ShareCR> shareCRsList = new ArrayList<>();
						shareCRsList.addAll(shareCRs);
						Collections.sort(shareCRsList, new ShareCRComparator());
						for(int h = 0; h < shareCRsList.size(); h++) {
							ShareCR shareCR = shareCRsList.get(h);
							if(!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id()!=this.mySession.getPerson().getPerson_Id()) {
								//this.notificationIndex++;
								this.shareCRService.gorulmeDurumuUpdateShareCR(shareCR);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<Likex> getSeenLikes() {
		this.session = this.sessionFactory.getCurrentSession();
		List<Likex> likexss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<Likex> likexs = share.getLikes();
			List<Likex> likexsList = new ArrayList<>();
			likexsList.addAll(likexs);
			Collections.sort(likexsList, new LikeComparator());
			for (int j = 0; j < likexsList.size(); j++) {
				Likex likex = likexsList.get(j);
				if (likex.isGorulme_Durumu()
						&& likex.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
					// notificationIndex++;
					likexss.add(likex);
				}
			}
		}
		return likexss;
	}

	@Override
	public List<ShareCommit> getSeenShareCommit() {
		this.session = this.sessionFactory.getCurrentSession();
		List<ShareCommit> shareCommitss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {

					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (shareCommit.isGorulme_Durumu()
							&& shareCommit.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
						shareCommitss.add(shareCommit);
					}
				}
			}
		}
		return shareCommitss;
	}

	@Override
	public List<ShareCR> getSeenShareCR() {
		this.session = this.sessionFactory.getCurrentSession();
		List<ShareCR> shareCRss = new ArrayList<>();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<Share> shares = criteria.list();
		for (int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);

			Set<ShareCommit> shareCommits = share.getShareCommits();
			List<ShareCommit> shareCommitsList = new ArrayList<>();
			shareCommitsList.addAll(shareCommits);
			Collections.sort(shareCommitsList, new ShareCommitComparator());
			if (!shareCommitsList.isEmpty()) {
				for (int j = 0; j < shareCommitsList.size(); j++) {

					ShareCommit shareCommit = shareCommitsList.get(j);

					Set<ShareCR> shareCRs = shareCommit.getShareCRs();
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCRs);
					Collections.sort(shareCRsList, new ShareCRComparator());

					if (!shareCRsList.isEmpty()) {
						ShareCR shareCRi = shareCRsList.get(shareCRsList.size() - 1);
						if (shareCRi.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
							int kalanIndex = 0;
							for (int x = 0; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (shareCR.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
									kalanIndex = x;
								}
							}
							for (int x = kalanIndex; x < shareCRsList.size(); x++) {
								ShareCR shareCR = shareCRsList.get(x);
								if (shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id() != this.mySession
										.getPerson().getPerson_Id()) {
									// notificationIndex++;
									shareCRss.add(shareCR);
								}
							}
						}
					}
				}
			}
		}

		Criteria criteriaa = this.session.createCriteria(ShareCR.class);
		criteriaa.add(Restrictions.eq("person", this.mySession.getPerson()));
		List<ShareCR> shareCRs = criteriaa.list();
		List<ShareCommit> shareCommits = new ArrayList<>();
		//shareCommits 'i distinct
		for (int i = 0; i < shareCRs.size(); i++) {
			ShareCR shareCR = shareCRs.get(i);
			ShareCommit shareCommit = shareCR.getShareCommit();
			shareCommits.add(shareCommit);
		}
		for (int a = 0; a < shareCommits.size(); a++) {
			ShareCommit shareCommit = shareCommits.get(a);
			List<ShareCR> shareCR2 = new ArrayList<>();
			List<ShareCR> shareCR3 = new ArrayList<>();
			Set<ShareCR> shareCRs2 = shareCommit.getShareCRs();
			shareCR2.addAll(shareCRs2);
			Collections.sort(shareCR2, new ShareCRComparator());

			if (!shareCR2.isEmpty()) {
				for (int i = 0; i < shareCR2.size(); i++) {
					ShareCR shareCr = shareCR2.get(i);
					if (shareCr.isGorulme_Durumu() && shareCr.getShare().getPerson().getPerson_Id() != this.mySession
							.getPerson().getPerson_Id()) {
						shareCR3.add(shareCr);
					}
				}
				if (!shareCR3.isEmpty()) {
					List<ShareCR> shareCRsList = new ArrayList<>();
					shareCRsList.addAll(shareCR3);
					Collections.sort(shareCRsList, new ShareCRComparator());
					boolean bizeAitDurum = false;
					int kalanIndex = 0;
					for (int x = 0; x < shareCRsList.size(); x++) {
						ShareCR shareCr = shareCRsList.get(x);
						if (shareCr.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
							bizeAitDurum = true;
							break;
						}
					}

					for (int x = 0; x < shareCRsList.size(); x++) {
						ShareCR shareCr = shareCRsList.get(x);
						if (shareCr.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
							kalanIndex = x;
						}
					}

					for (int x = kalanIndex; x < shareCRsList.size(); x++) {
						ShareCR shareCr = shareCRsList.get(x);
						if (shareCr.isGorulme_Durumu()
								&& shareCr.getPerson().getPerson_Id() != this.mySession.getPerson().getPerson_Id()) {
							//notificationIndex++;
							shareCRss.add(shareCr);
						}
					}
				}
			}
		}

		return shareCRss;
	}

	@Override
	public List<ShareCR> getFriendShareUnSeenCommit() {
		List<ShareCR> returnedShareCRList = new ArrayList<>();
		
		List<Person> myFriends = friendService.getMyFriends();
		for(int i = 0; i < myFriends.size(); i++) {
			Person person = this.personService.personuAl(myFriends.get(i).getPerson_Id());
			Set<Share> shares = person.getShares();
			List<Share> sharesList = new ArrayList<>();
			sharesList.addAll(shares);
			Collections.sort(sharesList,new ShareComparator());
			for(int j = 0; j < sharesList.size(); j++) {
				Share share = sharesList.get(j);
				Set<ShareCommit> shareCommits = share.getShareCommits();
				List<ShareCommit> shareCommitsList = new ArrayList<>();
				shareCommitsList.addAll(shareCommits);
				Collections.sort(shareCommitsList, new ShareCommitComparator());
				for(int f = 0; f < shareCommitsList.size(); f++) {
					ShareCommit shareCommit = shareCommitsList.get(f);
					if(shareCommit.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
						Set<ShareCR> shareCRs = shareCommit.getShareCRs();
						List<ShareCR> shareCRsList = new ArrayList<>();
						shareCRsList.addAll(shareCRs);
						Collections.sort(shareCRsList, new ShareCRComparator());
						for(int h = 0; h < shareCRsList.size(); h++) {
							ShareCR shareCR = shareCRsList.get(h);
							if(!shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id()!=this.mySession.getPerson().getPerson_Id()) {
								returnedShareCRList.add(shareCR);
							}
						}
					}
				}
			}
		}
		return returnedShareCRList;
	}

	public FriendService getFriendService() {
		return friendService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	@Override
	public List<ShareCR> getFriendShareSeenCommit() {
		List<ShareCR> returnedShareCRList = new ArrayList<>();
		
		List<Person> myFriends = friendService.getMyFriends();
		for(int i = 0; i < myFriends.size(); i++) {
			Person person = this.personService.personuAl(myFriends.get(i).getPerson_Id());
			Set<Share> shares = person.getShares();
			List<Share> sharesList = new ArrayList<>();
			sharesList.addAll(shares);
			Collections.sort(sharesList,new ShareComparator());
			for(int j = 0; j < sharesList.size(); j++) {
				Share share = sharesList.get(j);
				Set<ShareCommit> shareCommits = share.getShareCommits();
				List<ShareCommit> shareCommitsList = new ArrayList<>();
				shareCommitsList.addAll(shareCommits);
				Collections.sort(shareCommitsList, new ShareCommitComparator());
				for(int f = 0; f < shareCommitsList.size(); f++) {
					ShareCommit shareCommit = shareCommitsList.get(f);
					if(shareCommit.getPerson().getPerson_Id() == this.mySession.getPerson().getPerson_Id()) {
						Set<ShareCR> shareCRs = shareCommit.getShareCRs();
						List<ShareCR> shareCRsList = new ArrayList<>();
						shareCRsList.addAll(shareCRs);
						Collections.sort(shareCRsList, new ShareCRComparator());
						for(int h = 0; h < shareCRsList.size(); h++) {
							ShareCR shareCR = shareCRsList.get(h);
							if(shareCR.isGorulme_Durumu() && shareCR.getPerson().getPerson_Id()!=this.mySession.getPerson().getPerson_Id()) {
								returnedShareCRList.add(shareCR);
							}
						}
					}
				}
			}
		}
		return returnedShareCRList;
	}
	
	

}

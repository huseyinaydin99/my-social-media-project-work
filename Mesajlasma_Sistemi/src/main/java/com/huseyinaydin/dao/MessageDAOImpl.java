package com.huseyinaydin.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.comparator.CommitComparator;
import com.huseyinaydin.comparator.MessageBooleanComparator;
import com.huseyinaydin.comparator.MessageComparator;
import com.huseyinaydin.comparator.MessageDateComparator;
import com.huseyinaydin.model.Commit;
import com.huseyinaydin.model.Message;
import com.huseyinaydin.model.Person;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ProfileService;
import com.huseyinaydin.session.MySession;

@ManagedBean(name = "messageDAOImpl")
@Repository
public class MessageDAOImpl implements MessageDAO {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	@Autowired
	@Qualifier("commitDAO")
	private CommitDAO commitDAO;

	@Autowired
	@Qualifier("profileService")
	private ProfileService profileService;

	@Override
	public void sendMessage(Message message, Part uploadedFile, String fileNamex) {
		System.out.println("send messageye girdi");
		this.session = this.sessionFactory.getCurrentSession();

		Query query = this.session.createQuery("FROM Message as m where m.person=:per and m.to=:tu");// bizim başkasına
																										// gönderdiğimiz
																										// mesajlar
		query.setParameter("per", mySession.getPerson());
		query.setParameter("tu", personService.personuAl(mySession.getKim()).getPerson_Id());

		List<Message> messages = query.list();

		Query query2 = this.session.createQuery("FROM Message as m where m.person=:per and m.to=:tu");// başkasından
																										// aldığımız
																										// mesajlar
		query2.setParameter("per", personService.personuAl(mySession.getKim()));
		query2.setParameter("tu", mySession.getPerson().getPerson_Id());

		List<Message> messages2 = query2.list();

		List<Message> total = new ArrayList<>();
		if (messages.size() > 0 || messages2.size() > 0) {
			if (messages.size() > 0)
				for (int i = 0; i < messages.size(); i++) {
					total.add(messages.get(i));
				}

			if (messages2.size() > 0)
				for (int i = 0; i < messages2.size(); i++) {
					total.add(messages2.get(i));
				}
		}

		if (total.size() > 0) {
			// message.setIslemDurumu("Böyle bir mesaj var hemde : " +
			// total.size());
			Commit commit = new Commit();
			Message message2 = total.get(0);
			commit.setMessage(message2);
			commit.setPerson(personService.personuAl(mySession.getPerson().getPerson_Id()));
			commit.setTo(personService.personuAl(mySession.getKim()).getPerson_Id());
			commit.setCommit_Body(message.getMessage_Body());

			if (uploadedFile != null) {// dosya seçilmiş ise
				if (uploadedFile.getSize() <= 5120000) {
					String tersUzanti = "";
					String fileName = fileNamex;
					for (int i = fileName.length() - 1; i >= 0; i--) {
						if (fileName.charAt(i) != '.') {
							tersUzanti += fileName.charAt(i);
						} else if (fileName.charAt(i) == '.') {
							break;
						}
					}
					String duzUzanti = "";
					for (int i = tersUzanti.length() - 1; i >= 0; i--) {
						duzUzanti += tersUzanti.charAt(i);
					}
					if (duzUzanti.equals("jpg") || duzUzanti.equals("JPG") || duzUzanti.equals("jpeg")
							|| duzUzanti.equals("JPEG") || duzUzanti.equals("png") || duzUzanti.equals("PNG")) {
						try {
							copyFile(fileName, uploadedFile.getInputStream(), duzUzanti, true, total, commit);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						System.err.println("seçili dosya resim dosyası değil!");
					}
				} else {
					System.err.println("seçili dosya 5 mb den büyük");
					FacesMessage messagex = new FacesMessage("seçili dosya 5 mb den büyük");
					FacesContext.getCurrentInstance().addMessage(null, messagex);
				}
			} else {
				System.err.println("seçili dosya yok");
			}
			commitDAO.sendMessage(commit);
			message.setIslemDurumu("Mesaj gönderildi");
			System.out.println("mesaj gönderildi commit");
		} else {
			message.setPerson(personService.personuAl(mySession.getPerson().getPerson_Id()));
			message.setTo(personService.personuAl(mySession.getKim()).getPerson_Id());
			message.setGorulmeDurumu(false);
			if (uploadedFile != null) {// dosya seçilmiş ise
				if (uploadedFile.getSize() <= 5120000) {
					String tersUzanti = "";
					String fileName = fileNamex;
					for (int i = fileName.length() - 1; i >= 0; i--) {
						if (fileName.charAt(i) != '.') {
							tersUzanti += fileName.charAt(i);
						} else if (fileName.charAt(i) == '.') {
							break;
						}
					}
					String duzUzanti = "";
					for (int i = tersUzanti.length() - 1; i >= 0; i--) {
						duzUzanti += tersUzanti.charAt(i);
					}
					if (duzUzanti.equals("jpg") || duzUzanti.equals("JPG") || duzUzanti.equals("jpeg")
							|| duzUzanti.equals("JPEG") || duzUzanti.equals("png") || duzUzanti.equals("PNG")) {
						try {
							copyFile(fileName, uploadedFile.getInputStream(), duzUzanti, true, total, message);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						System.err.println("seçili dosya resim dosyası değil!");
						FacesMessage messagex = new FacesMessage("Seçili dosya resim dosyası değil!");
						FacesContext.getCurrentInstance().addMessage(null, messagex);
					}
				} else {
					System.err.println("seçili dosya 5 mb den büyük");
					FacesMessage messagex = new FacesMessage("seçili dosya 5 mb den büyük");
					FacesContext.getCurrentInstance().addMessage(null, messagex);
				}
			} else {
				System.err.println("seçili dosya yok");
			}
			this.session.save(message);
			message.setIslemDurumu("Mesaj gönderildi");
			System.out.println("mesaj gönderildi message");
		}
	}

	public void copyFile(String fileName, InputStream in, String uzanti, boolean gelenDurum, List<Message> total,
			Message messagex) {
		try {
			long photoIndex = 0;
			Collections.sort(total, new MessageComparator());
			if (total.size() > 0) {
				for (int i = 0; i < total.size(); i++) {
					Message message = total.get(i);
					TreeSet<Commit> treeSet = new TreeSet<>(new CommitComparator());
					Iterator<Commit> iterator = message.getCommits().iterator();
					while (iterator.hasNext()) {
						Commit commit = (Commit) iterator.next();
						treeSet.add(commit);
						System.out.println("commit huso");
						System.out.println(commit.getCommit_Id());
					}
					total.get(i).setCommits(treeSet);
					System.out.println("tree set");
					Iterator<Commit> iterator2 = message.getCommits().iterator();
					while (iterator2.hasNext()) {
						Commit commit = (Commit) iterator2.next();
						System.out.println("commit huso");
						System.out.println(commit.getCommit_Id());
					}
				}
			}

			for (int i = 0; i < total.size(); i++) {
				Message message = total.get(i);
				if (message.getCommits().size() > 0) {
					List<Commit> commits = new ArrayList<>();
					commits.addAll(message.getCommits());
					photoIndex = commits.get(commits.size() - 1).getCommit_Id();
				} else {
					photoIndex = message.getMessage_Id();
				}
			}
			try {
				messagex.setMessage_Photo(
						photoIndex + "_message_" + mySession.getPerson().getPerson_Id() + "." + uzanti);
			} catch (NullPointerException e) {

			}
			String path = "";
			if (gelenDurum)
				path = "C:\\Users\\husey\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
						+ photoIndex + "_message_" + mySession.getPerson().getPerson_Id() + "." + uzanti;
			else
				path = "C:\\Users\\husey\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
						+ photoIndex + "_message_" + mySession.getPerson().getPerson_Id() + "." + uzanti;
			OutputStream out = new FileOutputStream(new File(path));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.err.println("dosya oluştu ve kaydedildi! (:");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void copyFile(String fileName, InputStream in, String uzanti, boolean gelenDurum, List<Message> total,
			Commit commitx) {
		try {
			long photoIndex = 0;
			Collections.sort(total, new MessageComparator());
			if (total.size() > 0) {
				for (int i = 0; i < total.size(); i++) {
					Message message = total.get(i);
					TreeSet<Commit> treeSet = new TreeSet<>(new CommitComparator());
					Iterator<Commit> iterator = message.getCommits().iterator();
					while (iterator.hasNext()) {
						Commit commit = (Commit) iterator.next();
						treeSet.add(commit);
						System.out.println("commit huso");
						System.out.println(commit.getCommit_Id());
					}
					total.get(i).setCommits(treeSet);
					System.out.println("tree set");
					Iterator<Commit> iterator2 = message.getCommits().iterator();
					while (iterator2.hasNext()) {
						Commit commit = (Commit) iterator2.next();
						System.out.println("commit huso");
						System.out.println(commit.getCommit_Id());
					}
				}
			}

			for (int i = 0; i < total.size(); i++) {
				Message message = total.get(i);
				if (message.getCommits().size() > 0) {
					List<Commit> commits = new ArrayList<>();
					commits.addAll(message.getCommits());
					photoIndex = commits.get(commits.size() - 1).getCommit_Id();
				} else {
					photoIndex = message.getMessage_Id();
				}
			}
			try {
				commitx.setCommit_Photo(photoIndex + "_commit_" + mySession.getPerson().getPerson_Id() + "." + uzanti);
			} catch (NullPointerException e) {

			}
			String path = "";
			if (gelenDurum)
				path = "C:\\Users\\husey\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
						+ photoIndex + "_commit_" + mySession.getPerson().getPerson_Id() + "." + uzanti;
			else
				path = "C:\\Users\\husey\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
						+ photoIndex + "_message_" + mySession.getPerson().getPerson_Id() + "." + uzanti;
			OutputStream out = new FileOutputStream(new File(path));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.err.println("dosya oluştu ve kaydedildi! (:");
		} catch (IOException e) {
			System.err.println(e.getMessage());
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

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public CommitDAO getCommitDAO() {
		return commitDAO;
	}

	public void setCommitDAO(CommitDAO commitDAO) {
		this.commitDAO = commitDAO;
	}

	@Override
	public List<Message> mesajlariAl() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria c = this.session.createCriteria(Message.class, "message");
		c.add(Restrictions.or(Restrictions.eq("person", mySession.getPerson()),
				Restrictions.eq("to", personService.personuAl(mySession.getPerson().getPerson_Id()).getPerson_Id())));
		/*
		 * c.createAlias("message.commits", "c"); c.addOrder(Order.asc("c.commit_Id"));
		 * c.setFetchMode("commits", FetchMode.SELECT);
		 * c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 */

		Criteria c2 = this.session.createCriteria(Message.class, "message");
		c2.add(Restrictions.and(Restrictions.eq("person", personService.personuAl(mySession.getKim())),
				Restrictions.eq("to", mySession.getPerson().getPerson_Id())));
		/*
		 * c2.createAlias("message.commits", "c");
		 * c2.addOrder(Order.asc("c.commit_Id")); c2.setFetchMode("commits",
		 * FetchMode.SELECT); c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 */

		List<Message> messages = c.list();
		System.err.println("mesaj 1 size " + messages.size());
		List<Message> messages2 = c2.list();
		System.err.println("mesaj 2 size " + messages2.size());
		List<Message> total = new ArrayList<>();
		if (messages.size() > 0)
			for (int i = 0; i < messages.size(); i++) {
				total.add(messages.get(i));
			}

		if (messages2.size() > 0)
			for (int i = 0; i < messages2.size(); i++) {
				total.add(messages2.get(i));
			}

		for (int i = 0; i < total.size(); i++) {
			Message message = total.get(i);
			if (message.getPerson().getPerson_Id() == mySession.getPerson().getPerson_Id()) {
				message.setKimeGidecek(message.getTo());
			} else {
				message.setKimeGidecek(message.getPerson().getPerson_Id());
			}
		}

		Set<Message> messages3 = new LinkedHashSet<>();
		messages3.addAll(total);
		total.clear();
		total.addAll(messages3);

		List<Message> gorunenler = new ArrayList<>();
		List<Message> gorunmeyenler = new ArrayList<>();
		List<Message> bizimYazdiklarimiz = new ArrayList<>();

		boolean durum = false;
		for (int i = 0; i < total.size(); i++) {
			if (total.get(i).isGorulmeDurumu()
					&& total.get(i).getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()) {
				if (total.get(i).getCommits().isEmpty()) {
					gorunenler.add(total.get(i));
					System.err.println("görünenlere eklendi komitler boş");
				} else {
					durum = false;
					Iterator<Commit> commits = total.get(i).getCommits().iterator();
					while (commits.hasNext()) {
						Commit commit = (Commit) commits.next();
						if (!commit.isGorulmeDurumu()
								&& commit.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()) {
							durum = true;
							break;
						}
					}
					if (!durum) {
						gorunenler.add(total.get(i));
						System.err.println("görünenlere eklendi komitler dolu");
					} else {
						gorunmeyenler.add(total.get(i));
						System.err.println("görünmyenlere eklendi komitler dolu");
					}
				}
			} else if (!total.get(i).isGorulmeDurumu()
					&& total.get(i).getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()) {
				if (total.get(i).getCommits().isEmpty()) {
					gorunmeyenler.add(total.get(i));
				} else {
					durum = false;
					Iterator<Commit> commits = total.get(i).getCommits().iterator();
					while (commits.hasNext()) {
						Commit commit = (Commit) commits.next();
						if (!commit.isGorulmeDurumu()
								&& commit.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()) {
							durum = true;
							break;
						}
					}
					if (!durum) {
						gorunenler.add(total.get(i));
						System.err.println("görünenlere eklendi komitler dolu");
					} else {
						gorunmeyenler.add(total.get(i));
						System.err.println("görünmyenlere eklendi komitler dolu");
					}
				}
			} else if (total.get(i).getPerson().getPerson_Id() == mySession.getPerson().getPerson_Id()) {
				bizimYazdiklarimiz.add(total.get(i));
			}
		}
		total.clear();

		
		
		for (int i = 0; i < gorunmeyenler.size(); i++) {
			if (gorunmeyenler.get(i).isGorulmeDurumu() == true) {
				gorunmeyenler.get(i).setGorulmeDurumu(false);
			}
			total.add(gorunmeyenler.get(i));
			System.out.println(
					"slm " + gorunmeyenler.get(i).isGorulmeDurumu() + " " + gorunmeyenler.get(i).getMessage_Body());
		}

		for (int i = 0; i < gorunenler.size(); i++) {
			if (gorunenler.get(i).isGorulmeDurumu() == false) {
				gorunenler.get(i).setGorulmeDurumu(true);
			}
			total.add(gorunenler.get(i));
			System.out
					.println("nbr " + gorunenler.get(i).isGorulmeDurumu() + " " + gorunenler.get(i).getMessage_Body());
		}

		for (int i = 0; i < bizimYazdiklarimiz.size(); i++) {
			total.add(bizimYazdiklarimiz.get(i));
			System.out.println("nbr " + bizimYazdiklarimiz.get(i).isGorulmeDurumu() + " "
					+ bizimYazdiklarimiz.get(i).getMessage_Body());
		}

		for (int i = 0; i < total.size(); i++) {
			System.out.println(total.get(i).isGorulmeDurumu() + " " + total.get(i).getMessage_Body());
		}
		System.err.println("total boyutu " + total.size());
		List<Message> total2 = new ArrayList<>();
		for (int i = 0; i < total.size(); i++) {
			Message message = total.get(i);
			Set<Commit> commits = message.getCommits();
			List<Commit> commitsList = new ArrayList<>();
			commitsList.addAll(commits);
			Collections.sort(commitsList, new CommitComparator());
			Commit sonCommit = null;
			try {
				sonCommit = commitsList.get(commitsList.size() - 1);
			} catch (ArrayIndexOutOfBoundsException e) {

			}
			if (sonCommit != null) {
				if (sonCommit.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()
						&& !sonCommit.isGorulmeDurumu()) {
					message.setGorulmeDurumu2(false);
				} else
					message.setGorulmeDurumu2(true);
				
				Date cTarih = sonCommit.getCommitKayitTarihi();
				Date cSaat = sonCommit.getCommitKayitSaati();
				Date dateTime = new Date(cTarih.getDay(),cTarih.getMonth(),cTarih.getYear(),cSaat.getHours(),cSaat.getMinutes(),cSaat.getSeconds());
				message.setSonMesajTarihi(dateTime);
				message.setMessage_Body2(sonCommit.getCommit_Body());
			}
			else {
				if (message.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()
						&& !message.isGorulmeDurumu()) {
					message.setGorulmeDurumu2(false);
				} else
					message.setGorulmeDurumu2(true);
				Date cTarih = message.getMessageKayitTarihi();
				Date cSaat = message.getMessageKayitSaati();
				Date dateTime = new Date(cTarih.getDay(),cTarih.getMonth(),cTarih.getYear(),cSaat.getHours(),cSaat.getMinutes(),cSaat.getSeconds());
				message.setSonMesajTarihi(dateTime);
				message.setMessage_Body2(message.getMessage_Body());
			}
			total2.add(message);
			// System.err.println(" " + message.getPerson().getPerson_ProfilePhoto());
		}
		Collections.sort(total2, new MessageDateComparator());
		Collections.sort(total2, new MessageBooleanComparator());
		return total2;
	}

	@Override
	public List<Message> mesajlariVeIcerikleriAl() {
		this.session = this.sessionFactory.getCurrentSession();
		System.out.println(mySession.getPerson());
		System.out.println(mySession.getKim());
		if (personService != null)
			System.out.println("null değil");
		System.out.println(personService.personuAl(mySession.getKim()).getPerson_Id());
		Criteria c = this.session.createCriteria(Message.class, "message");
		c.add(Restrictions.and(Restrictions.eq("person", mySession.getPerson()),
				Restrictions.eq("to", personService.personuAl(mySession.getKim()).getPerson_Id())));
		/*
		 * c.createAlias("message.commits", "c"); c.addOrder(Order.asc("c.commit_Id"));
		 * c.setFetchMode("commits", FetchMode.SELECT);
		 * c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 */

		Criteria c2 = this.session.createCriteria(Message.class, "message");
		c2.add(Restrictions.and(Restrictions.eq("person", personService.personuAl(mySession.getKim())),
				Restrictions.eq("to", mySession.getPerson().getPerson_Id())));
		/*
		 * c2.createAlias("message.commits", "c");
		 * c2.addOrder(Order.asc("c.commit_Id")); c2.setFetchMode("commits",
		 * FetchMode.SELECT); c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 */

		List<Message> messages = c.list();
		System.err.println("============= > mesaj 1 boyutu : " + messages.size());
		List<Message> messages2 = c2.list();
		System.err.println("============= > mesaj 2 boyutu : " + messages2.size());
		System.err.println("============= > kim " + mySession.getKim());
		System.err.println("============= > biz " + mySession.getPerson().getPerson_Id());
		List<Message> total = new ArrayList<>();
		total.clear();
		if (messages.size() > 0)
			for (int i = 0; i < messages.size(); i++) {
				total.add(messages.get(i));
			}

		if (messages2.size() > 0)
			System.err.println("message 2 o dan büyük");
		for (int i = 0; i < messages2.size(); i++) {
			total.add(messages2.get(i));
			System.err.println("totale eklendi for a girdi!");
		}

		if (total.size() > 0) {
			System.err.println("total o dan büyük");
			Iterator<Commit> iterator = total.get(0).getCommits().iterator();
			while (iterator.hasNext()) {
				Commit commit = (Commit) iterator.next();
				System.out.println("hüseyin ***");
				System.out.println(commit + " " + commit.getCommit_Body() + " " + commit.getPerson().getPerson_Adi());
			}
		}
		Collections.sort(total, new MessageComparator());
		if (total.size() > 0) {
			System.err.println("total o dan büyük");
			Message message = total.get(0);
			TreeSet<Commit> treeSet = new TreeSet<>(new CommitComparator());
			Iterator<Commit> iterator = message.getCommits().iterator();
			while (iterator.hasNext()) {
				Commit commit = (Commit) iterator.next();
				treeSet.add(commit);
				System.out.println("commit huso");
				System.out.println(commit.getCommit_Id());
			}
			total.get(0).setCommits(treeSet);
			System.out.println("tree set");
			Iterator<Commit> iterator2 = message.getCommits().iterator();
			while (iterator2.hasNext()) {
				Commit commit = (Commit) iterator2.next();
				System.out.println("commit huso");
				System.out.println(commit.getCommit_Id());
			}
		}
		return total;
	}

	@Override
	public long mesajlarToplaminiAl() {

		List<Message> messages = this.mesajlariAl();

		Collections.sort(messages, new MessageComparator());
		if (messages.size() > 0) {
			for (int i = 0; i < messages.size(); i++) {
				Message message = messages.get(i);
				TreeSet<Commit> treeSet = new TreeSet<>(new CommitComparator());
				Iterator<Commit> iterator = message.getCommits().iterator();
				while (iterator.hasNext()) {
					Commit commit = (Commit) iterator.next();
					treeSet.add(commit);
					System.out.println("commit huso");
					System.out.println(commit.getCommit_Id());
				}
				messages.get(i).setCommits(treeSet);
				System.out.println("tree set");
				Iterator<Commit> iterator2 = message.getCommits().iterator();
				while (iterator2.hasNext()) {
					Commit commit = (Commit) iterator2.next();
					System.out.println("commit huso");
					System.out.println(commit.getCommit_Id());
				}
			}
		}

		int gorulmeyenIndex = 0;
		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			System.err.println("mesaj : " + message.getPerson().getPerson_Id() + " " + message.getMessage_Body() + " "
					+ message.isGorulmeDurumu());
			String sessionId = String.valueOf(mySession.getPerson().getPerson_Id());
			String messageId = String.valueOf(message.getPerson().getPerson_Id());

			if (message.isGorulmeDurumu() == false && !sessionId.equals(messageId) && message.getCommits().size() < 1) {
				System.out.println("yakalandı message session person id " + mySession.getPerson().getPerson_Id());
				gorulmeyenIndex++;
			}
			List<Commit> commits = new ArrayList<>();
			commits.addAll(message.getCommits());
			if (commits.size() > 0) {
				Commit commit = commits.get(commits.size() - 1);
				String commitId = String.valueOf(commit.getPerson().getPerson_Id());
				if (commit.isGorulmeDurumu() == false && !sessionId.equals(commitId)) {
					System.out.println("yakalandı commit session person id " + mySession.getPerson().getPerson_Id());
					gorulmeyenIndex++;
				}
				for (int j = 0; j < commits.size(); j++) {
					commit = commits.get(j);
					System.err.println("comit : " + commit.getPerson().getPerson_Id() + " " + commit.getCommit_Body()
							+ " " + commit.isGorulmeDurumu());
				}
			}
		}
		if (gorulmeyenIndex > 0) {

		}

		return gorulmeyenIndex;
	}

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	@Override
	public void updateMessageAndCommitGorulmeDurumu(Message message) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria c = this.session.createCriteria(Message.class, "message");
		c.add(Restrictions.and(Restrictions.eq("person", mySession.getPerson()),
				Restrictions.eq("to", personService.personuAl(mySession.getKim()).getPerson_Id())));

		Criteria c2 = this.session.createCriteria(Message.class, "message");
		c2.add(Restrictions.and(Restrictions.eq("person", personService.personuAl(mySession.getKim())),
				Restrictions.eq("to", mySession.getPerson().getPerson_Id())));

		List<Message> messages = c.list();
		List<Message> messages2 = c2.list();
		List<Message> total = new ArrayList<>();
		total.clear();
		if (messages.size() > 0)
			for (int i = 0; i < messages.size(); i++) {
				total.add(messages.get(i));
			}
		if (messages2.size() > 0)
			for (int i = 0; i < messages2.size(); i++) {
				total.add(messages2.get(i));
			}
		for (int i = 0; i < total.size(); i++) {
			Message messagex = total.get(i);
			if (messagex.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id())
				messagex.setGorulmeDurumu(true);
			Set<Commit> commits = messagex.getCommits();
			List<Commit> commitsList = new ArrayList<>();
			commitsList.addAll(commits);
			Collections.sort(commitsList, new CommitComparator());
			for (int j = 0; j < commitsList.size(); j++) {
				Commit commit = commitsList.get(j);
				if (commit.getPerson().getPerson_Id() != mySession.getPerson().getPerson_Id()) {
					commit.setGorulmeDurumu(true);
					this.session.update(commit);
				}
			}
			/*
			 * if(messagex.getPerson().getPerson_Id() !=
			 * mySession.getPerson().getPerson_Id()) this.session.update(messagex);
			 */
		}
		/*
		 * message.setGorulmeDurumu(true); Set<Commit> commits = message.getCommits();
		 * List<Commit> commitsList = new ArrayList<>(); commitsList.addAll(commits);
		 * Collections.sort(commitsList, new CommitComparator()); for(int j = 0; j <
		 * commitsList.size(); j++) { Commit commit = commitsList.get(j);
		 * commit.setGorulmeDurumu(true); this.session.update(commit); }
		 * this.session.update(message);
		 */
	}

	@Override
	public void deleteMessage(Message message) {
		this.session = this.sessionFactory.getCurrentSession();
		this.session.delete(message);
	}

}

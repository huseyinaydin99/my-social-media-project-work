package com.huseyinaydin.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huseyinaydin.comparator.CommitComparator;
import com.huseyinaydin.comparator.MessageComparator;
import com.huseyinaydin.comparator.ShareComparator;
import com.huseyinaydin.comparator.ShareComparatorDate;
import com.huseyinaydin.comparator.ShareImagesComparator;
import com.huseyinaydin.comparator.ShareReverseComparator;
import com.huseyinaydin.model.Commit;
import com.huseyinaydin.model.Message;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareImage;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ProfileService;
import com.huseyinaydin.session.MySession;

@Repository
public class ShareDAOImpl implements ShareDAO {

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

	private static long sShare_Id = 0;

	@Autowired
	@Qualifier("friendDAO")
	private FriendDAO friendDAO;
	
	public ShareDAOImpl() {
		super();
	}

	@Override
	public void saveShare(String shareText, Part uploadedFiles) throws ServletException, IOException {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		List<Share> total = criteria.list();

		Share share = new Share();
		share.setShare_Text(shareText);

		String filesNames = "";

		share.setShare_Images(filesNames);
		share.setPerson(mySession.getPerson());
		share.setShare_Date(new Date());

		List<ShareImage> shareImages = new ArrayList<>();

		if (uploadedFiles != null) {
			Collection<Part> parts = this.getAllParts(uploadedFiles);
			List<Part> listPart = new ArrayList<>();
			listPart.addAll(parts);
			for (int i = 0; i < listPart.size(); i++) {
				Part part = listPart.get(i);

				String fileName = this.getFileName(part);
				String tersUzanti = "";
				for (int j = fileName.length() - 1; j >= 0; j--) {
					if (fileName.charAt(j) == '.')
						break;
					else
						tersUzanti += fileName.charAt(j);
				}
				String duzUzanti = "";
				for (int j = tersUzanti.length() - 1; j >= 0; j--) {
					duzUzanti += tersUzanti.charAt(j);
				}

				long photoIndex = 0;
				for (int x = 0; x < total.size(); x++) {
					Share message = total.get(x);
					photoIndex = message.getShare_Id();
				}

				filesNames = filesNames + photoIndex + "_share_" + mySession.getPerson().getPerson_Id() + "_"
						+ String.valueOf(i) + "." + duzUzanti + " - ";
				ShareImage shareImage = new ShareImage();
				shareImage.setImage(photoIndex + "_share_" + mySession.getPerson().getPerson_Id() + "_"
						+ String.valueOf(i) + "." + duzUzanti);
				shareImages.add(shareImage);
				this.copyFile(fileName, part.getInputStream(), duzUzanti, total, i);
			}
		}

		this.session.save(share);
		for (int i = 0; i < shareImages.size(); i++) {
			ShareImage shareImage = shareImages.get(i);
			shareImage.setShare(share);
			// share.getImages().add(shareImage);
			this.session.save(shareImage);
		}
	}

	public String getFileName(Part part) throws ServletException, IOException {
		String filename = "";
		try {
			Collection<Part> parts = this.getAllParts(part);
			List<Part> parts2 = new ArrayList<>();
			parts2.addAll(parts);
			for (int i = 0; i < parts2.size(); i++) {
				Part partx = parts2.get(i);
				for (String cd : partx.getHeader("content-disposition").split(";")) {
					if (cd.trim().startsWith("filename")) {
						filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
						return filename;
					}
				}
			}

		} catch (NullPointerException e) {

		}
		return "";
	}

	public void copyFile(String fileName, InputStream in, String uzanti, List<Share> total, int fileIndex) {
		try {
			long photoIndex = 0;
			Collections.sort(total, new ShareComparator());
			if (total.size() > 0) {

			}

			for (int i = 0; i < total.size(); i++) {
				Share message = total.get(i);
				photoIndex = message.getShare_Id();
			}
			/*
			 * try { sharex.setShare_Images( photoIndex + "_share_" +
			 * mySession.getPerson().getPerson_Id() + "_" + String.valueOf(fileIndex) + "."
			 * + uzanti); } catch (NullPointerException e) {
			 * 
			 * }
			 */
			String path = "D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
					+ photoIndex + "_share_" + mySession.getPerson().getPerson_Id() + "_" + String.valueOf(fileIndex)
					+ "." + uzanti;
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

	public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
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
	public List<Share> getMyShare() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.personService.personuAl(this.mySession.getPerson())));
		List<Share> shares = criteria.list();
		List<Share> reverseShare = new ArrayList<>();
		for (int i = shares.size() - 1; i >= 0; i--) {
			reverseShare.add(shares.get(i));
		}
		return reverseShare;
	}

	@Override
	public List<Share> getProfileShare() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameterOne = params.get("profileID");
		long prmtr = 0;
		try {
			prmtr = Long.parseLong(parameterOne);
		} catch (NumberFormatException e) {

		}

		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.personService.personuAl(prmtr)));
		List<Share> shares = criteria.list();
		List<Share> returnedShares = new ArrayList<>();
		for (int i = 0; i < shares.size(); i++) {
			long id = shares.get(i).getPerson().getPerson_Id();
			byte friendResult = friendDAO.isFriend(id);
			if (friendResult == 2) {
				returnedShares.add(shares.get(i));
			}
		}
		
		List<Share> reverseShare = new ArrayList<>();
		for (int i = returnedShares.size() - 1; i >= 0; i--) {
			reverseShare.add(returnedShares.get(i));
		}
		return reverseShare;
	}

	@Override
	public List<Share> getOtherShare() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(
				Restrictions.not(Restrictions.eq("person", this.personService.personuAl(this.mySession.getPerson()))));
		List<Share> shares = criteria.list();
		List<Share> returnedShares = new ArrayList<>();
		for (int i = 0; i < shares.size(); i++) {
			long id = shares.get(i).getPerson().getPerson_Id();
			byte friendResult = friendDAO.isFriend(id);
			if (friendResult == 2) {
				returnedShares.add(shares.get(i));
			}
		}
		Collections.sort(returnedShares, new ShareReverseComparator());
		return returnedShares;
	}

	@Override
	public Share getShare(long shareId) {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("share_Id", shareId));
		List<Share> shares = criteria.list();
		Share share = null;
		if (!shares.isEmpty())// dolu ise
			share = shares.get(0);
		return share;
	}

	@Override
	public Share getShare() {

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameterOne = params.get("shareId");

		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("share_Id", parameterOne));
		List<Share> shares = criteria.list();
		Share share = null;
		if (!shares.isEmpty())// dolu ise
			share = shares.get(0);
		return share;
	}

	@Override
	public List<Share> getShares() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameterOne = params.get("shareId");
		if (parameterOne != null)
			sShare_Id = Long.parseLong(parameterOne);
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("share_Id", sShare_Id));
		List<Share> shares = criteria.list();
		return shares;
	}

	public FriendDAO getFriendDAO() {
		return friendDAO;
	}

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	@Override
	public List<ShareImage> getShareImageList() {
		this.session = this.sessionFactory.getCurrentSession();
		Criteria criteria = this.session.createCriteria(Share.class);
		criteria.add(Restrictions.eq("person", this.personService.personuAl(this.mySession.getPerson())));
		List<Share> shares = criteria.list();
		List<ShareImage> shareImages = new ArrayList<>();
		Collections.sort(shares,new ShareComparator());
		for(int i = 0; i < shares.size(); i++) {
			Share share = shares.get(i);
			Set<ShareImage> shareImagesSet = share.getImages();
			shareImages.addAll(shareImagesSet);
			Collections.sort(shareImages,new ShareImagesComparator());
		}
		return shareImages;
	}

}

package com.huseyinaydin.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.faces.validator.ValidatorException;
import javax.faces.validator.Validator;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Part;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.service.ProfileService;
import com.huseyinaydin.session.MySession;

@ManagedBean(name = "fileUpload")
public class FileUpload implements Filter, Validator {
	static {
		System.load(new File(
				"D:/yedekss/Documents/workspace-sts-3.8.4.RELEASE/Mesajlasma_Sistemi/WebContent/WEB-INF/lib/x64/"
						+ Core.NATIVE_LIBRARY_NAME + ".dll").getAbsolutePath());
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private UploadedFile uploadedFile;
	private UploadedFile uploadedFile2;

	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	@Autowired
	@Qualifier("profileService")
	private ProfileService profileService;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public UploadedFile getUploadedFile2() {
		return uploadedFile2;
	}

	public void setUploadedFile2(UploadedFile uploadedFile2) {
		this.uploadedFile2 = uploadedFile2;
	}

	public void upload() throws IOException, URISyntaxException {
		try {
			String tersUzanti = "";
			String fileName = uploadedFile.getFileName();
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
			if (uploadedFile != null && uploadedFile.getSize() <= 5120000) {
				if (duzUzanti.equals("jpg") || duzUzanti.equals("JPG") || duzUzanti.equals("jpeg")
						|| duzUzanti.equals("JPEG") || duzUzanti.equals("png") || duzUzanti.equals("PNG")) {

					try {
						copyFile(uploadedFile.getFileName(), uploadedFile.getInputstream(), duzUzanti, true);
						System.err.println("kopyalando");

					} catch (IOException e) {
						e.printStackTrace();
						System.err.println("hata var! reis");
					}
				} else {
					FacesMessage message = new FacesMessage("Dosya resim dosyası değil!");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} else {
				FacesMessage message = new FacesMessage("Dosya 5 MB den büyük olmamalı!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (NullPointerException e) {

		}
	}

	public void upload2() throws IOException, URISyntaxException {
		try {
			String tersUzanti = "";
			String fileName = uploadedFile2.getFileName();
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
			System.err.println("uzanti" + duzUzanti);
			if (uploadedFile2 != null && uploadedFile2.getSize() <= 5120000) {
				if (duzUzanti.equals("jpg") || duzUzanti.equals("JPG") || duzUzanti.equals("jpeg")
						|| duzUzanti.equals("JPEG") || duzUzanti.equals("png") || duzUzanti.equals("PNG")) {
					/*
					 * Path file = Paths.get(new URI("/resources/images/pro" +
					 * ".jpg")); Files.copy(uploadedFile.getInputstream(), file,
					 * StandardCopyOption.REPLACE_EXISTING);
					 */

					try {
						copyFile2(uploadedFile2.getFileName(), uploadedFile2.getInputstream(), duzUzanti, false);
						System.err.println("kopyalando");

					} catch (IOException e) {
						e.printStackTrace();
						System.err.println("hata var! reis");
					}
				} else {
					FacesMessage message = new FacesMessage("Dosya resim dosyası değil!");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} else {
				FacesMessage message = new FacesMessage("Dosya 5 MB den büyük olmamalı!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (NullPointerException e) {

		}
	}

	public MySession getMySession() {
		return mySession;
	}

	public void setMySession(MySession mySession) {
		this.mySession = mySession;
	}

	public void copyFile(String fileName, InputStream in, String uzanti, boolean gelenDurum) {
		try {

			String path = null;
			if (gelenDurum)
				path = "D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\profil_"
						+ mySession.getPerson().getPerson_Id() + "." + uzanti;
			else
				path = "D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\banner_"
						+ mySession.getPerson().getPerson_Id() + "." + uzanti;
			Person person = mySession.getPerson();
			if (new File(
					"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
							+ person.getPerson_ProfilePhoto()).exists()) {
				new File(
						"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
								+ person.getPerson_ProfilePhoto())
										.renameTo(new File(
												"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
														+ "_2_" + person.getPerson_ProfilePhoto()));
			}

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

			// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			CascadeClassifier cascadeFaceClassifier = new CascadeClassifier(
					"D:\\yedekss\\Documents\\a\\haarcascade_frontalface_alt.xml");
			// Mat mat = Highgui.imread(path);
			/*if(path == null) {
				FacesMessage message = new FacesMessage("Başarısız "," yol boş geldi null");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			else {
				FacesMessage message = new FacesMessage(path," yol dolu geldi null değil!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}*/
			/*File file = new File(path);
			if(file.exists()) {
				FacesMessage message = new FacesMessage(path," dosya var");
				FacesContext.getCurrentInstance().addMessage(null, message);
				if(file.canRead()) {
					FacesMessage message2 = new FacesMessage(path," dosya okunabilir");
					FacesContext.getCurrentInstance().addMessage(null, message2);
				}
			}
			else {
				FacesMessage message = new FacesMessage(path," yol dolu geldi null değil!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}*/
			Mat mat = Imgcodecs.imread(path);
			//Imgcodecs.imwrite("C:\\Users\\Hüseyin\\Desktop\\a\\a.png", mat);
			MatOfRect faces = new MatOfRect();
			cascadeFaceClassifier.detectMultiScale(mat, faces);
			int faceIndex = 0;
			boolean durumsx = false;
			for (Rect rect : faces.toArray()) {
				durumsx = true;
				faceIndex++;
			}
			/*if(durumsx) {
				FacesMessage message = new FacesMessage(path,String.valueOf(faceIndex) + " adet yüz tespit edildi");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}*/
			if (faceIndex > 1) {
				FacesMessage message = new FacesMessage("Başarısız ", fileName
						+ " Resim dosyasında birden fazla yüz tespit edildi. Düzgün bir profil resmi için bir adet yüz olan fotoğraf yükleyiniz.!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				cascadeFaceClassifier = null;
				mat = null;
				new File(path).delete();
				if (new File("D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\"
						+ "WebContent\\resources\\images\\" + "_2_" + person.getPerson_ProfilePhoto()).exists()) {
					new File(
							"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
									+ "_2_" + person.getPerson_ProfilePhoto())
											.renameTo(new File(
													"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
															+ person.getPerson_ProfilePhoto()));
				}
			} else if (faceIndex == 1) {
				if (gelenDurum) {
					person.setPerson_ProfilePhoto("profil_" + mySession.getPerson().getPerson_Id() + "." + uzanti);
					profileService.saveProfilePhoto(person);
				} else {
					person.setPerson_BannerPhoto("banner_" + mySession.getPerson().getPerson_Id() + "." + uzanti);
					profileService.saveBannerPhoto(person);
				}
				FacesMessage message = new FacesMessage("Başarılı ", fileName + " dosya yüklendi");
				FacesContext.getCurrentInstance().addMessage(null, message);
				new File("D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\"
						+ "WebContent\\resources\\images\\" + "_2_" + person.getPerson_ProfilePhoto()).delete();
			} else if (faceIndex == 0) {
				FacesMessage message = new FacesMessage("Başarısız ", fileName
						+ " Resim dosyasında yüz tespit edilemedi. Düzgün bir profil resmi için bir adet yüz olan fotoğraf yükleyiniz.!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				cascadeFaceClassifier = null;
				mat = null;
				new File(path).delete();
				if (new File("D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\"
						+ "WebContent\\resources\\images\\" + "_2_" + person.getPerson_ProfilePhoto()).exists()) {
					new File(
							"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
									+ "_2_" + person.getPerson_ProfilePhoto())
											.renameTo(new File(
													"D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\"
															+ person.getPerson_ProfilePhoto()));
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void copyFile2(String fileName, InputStream in, String uzanti, boolean gelenDurum) {
		try {
			String path = null;
			if (gelenDurum)
				path = "D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\profil_"
						+ mySession.getPerson().getPerson_Id() + "." + uzanti;
			else
				path = "D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\WebContent\\resources\\images\\banner_"
						+ mySession.getPerson().getPerson_Id() + "." + uzanti;
			Person person = mySession.getPerson();

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

			// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			CascadeClassifier cascadeFaceClassifier = new CascadeClassifier(
					"D:\\yedekss\\Documents\\a\\haarcascade_frontalface_alt.xml");
			// Mat mat = Highgui.imread(path);
			/*if(path == null) {
				FacesMessage message = new FacesMessage("Başarısız "," yol boş geldi null");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			else {
				FacesMessage message = new FacesMessage(path," yol dolu geldi null değil!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}*/
			/*File file = new File(path);
			if(file.exists()) {
				FacesMessage message = new FacesMessage(path," dosya var");
				FacesContext.getCurrentInstance().addMessage(null, message);
				if(file.canRead()) {
					FacesMessage message2 = new FacesMessage(path," dosya okunabilir");
					FacesContext.getCurrentInstance().addMessage(null, message2);
				}
			}
			else {
				FacesMessage message = new FacesMessage(path," yol dolu geldi null değil!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}*/
			person.setPerson_BannerPhoto("banner_" + mySession.getPerson().getPerson_Id() + "." + uzanti);
			profileService.saveBannerPhoto(person);
			FacesMessage message = new FacesMessage("Başarılı ", fileName + " dosya yüklendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
			new File("D:\\yedekss\\Documents\\workspace-sts-3.8.4.RELEASE\\Mesajlasma_Sistemi\\"
					+ "WebContent\\resources\\images\\" + "_2_" + person.getPerson_BannerPhoto()).delete();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		Part part = (Part) arg2;
		if (part.getSize() > 5210) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dosya boyutu sınırdan büyük.",
					"Dosya boyutu sınırdan büyük."));
		}
		if (!"image/jpeg".equals(part.getContentType()) || !"image/png".equals(part.getContentType())) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "JPG veya PNG Dosyası Değil",
					"JPG veya PNG Dosyası Değil"));
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest requset, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		requset.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(requset, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}

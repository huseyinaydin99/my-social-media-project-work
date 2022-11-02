package com.huseyinaydin.service;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.ProfileDAO;
import com.huseyinaydin.model.Person;

@ManagedBean(name = "profileService")
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	@Qualifier("profileDAO")
	private ProfileDAO profileDAO;

	@Autowired
	@Qualifier("personService")
	private PersonService personService;

	@Transactional
	@Override
	public void saveProfilePhoto(Person person) {
		this.profileDAO.saveProfilePhoto(person);
	}

	public ProfileDAO getProfileDAO() {
		return profileDAO;
	}

	public void setProfileDAO(ProfileDAO profileDAO) {
		this.profileDAO = profileDAO;
	}

	@Override
	public String getProfilePhoto() {
		String profilePhoto = "";
		try {
			profilePhoto = this.profileDAO.getProfilePhoto();
		} catch (NullPointerException e) {
			return "";
		}
		try {
			if (profilePhoto.length() > 0)
				return "/resources/images/" + this.profileDAO.getProfilePhoto();
			else
				return "/resources/images/default_profile.png";
		} catch (NullPointerException e) {
			return "/resources/images/default_profile.png";
		}
	}

	public String getProfilePhoto(long personId) {
		Person person = this.personService.personuAl(personId);
		if (person.getPerson_ProfilePhoto().length() > 0)
			return "/resources/images/" + person.getPerson_ProfilePhoto();
		else
			return "/resources/images/default_profile.png";
	}

	@Transactional
	@Override
	public void saveBannerPhoto(Person person) {
		this.profileDAO.saveBannerPhoto(person);
	}

	@Transactional
	@Override
	public String getBannerPhoto() {
		String bannerhoto = this.profileDAO.getBannerPhoto();
		try {
			if (bannerhoto.length() > 0)
				return "/resources/images/" + bannerhoto;
			else
				return "/resources/images/default_banner.jpg";
		} catch (NullPointerException e) {
			return "/resources/images/default_banner.jpg";
		}
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}

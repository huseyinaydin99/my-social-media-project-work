package com.huseyinaydin.dao;

import com.huseyinaydin.model.Person;

public interface ProfileDAO {
	public void saveProfilePhoto(Person person);
	public void saveBannerPhoto(Person person);
	public String getProfilePhoto();
	public String getBannerPhoto();
}

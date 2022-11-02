package com.huseyinaydin.service;

import com.huseyinaydin.model.Person;

public interface ProfileService {
	public void saveProfilePhoto(Person person);
	public void saveBannerPhoto(Person person);
	public String getProfilePhoto();
	public String getBannerPhoto();
}

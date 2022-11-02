package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.Person;

public interface PersonDAO {
	public void personKaydet(Person person);
	public List<Person> personKaydiniAl(long id);
	public Person personuAl(Person person);
	public Person personuAl(long id);
}

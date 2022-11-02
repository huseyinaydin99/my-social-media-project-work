package com.huseyinaydin.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.PersonDAO;
import com.huseyinaydin.model.Person;

@Service
@ManagedBean(name = "personService")
@RequestScoped
public class PersonServiceImpl implements PersonService {

	@Autowired
	@Qualifier("personDAO")
	private PersonDAO personDAO;
	
	@Transactional
	@Override
	public void personKaydet(Person person) {
		this.personDAO.personKaydet(person);
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Transactional
	@Override
	public List<Person> personKaydiniAl(long id) {
		// TODO Auto-generated method stub
		return this.personDAO.personKaydiniAl(id);
	}

	@Transactional
	@Override
	public Person personuAl(Person person) {
		return this.personDAO.personuAl(person);
	}

	@Transactional
	@Override
	public Person personuAl(long id) {
		return this.personDAO.personuAl(id);
	}

}

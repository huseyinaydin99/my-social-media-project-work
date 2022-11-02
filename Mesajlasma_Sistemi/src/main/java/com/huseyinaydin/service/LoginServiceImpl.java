package com.huseyinaydin.service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.LoginDAO;
import com.huseyinaydin.model.Person;

@Service
@ManagedBean(name = "loginService")
@RequestScoped
public class LoginServiceImpl implements LoginService {

	@Autowired
	@Qualifier("loginDAO")
	private LoginDAO loginDAO;
	@Transactional
	@Override
	public void loginIn(Person person) {
		this.loginDAO.loginIn(person);
	}
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

}

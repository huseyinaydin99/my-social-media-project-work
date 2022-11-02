package com.huseyinaydin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.SearchDAO;
import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.SearchModel;

public class SearchServiceImpl implements SearchService {

	@Autowired
	@Qualifier("searchDAO")
	private SearchDAO searchDAO;
	
	
	@Transactional
	@Override
	public List<Person> searchPerson(SearchModel searchModel) {
		// TODO Auto-generated method stub
		return this.searchDAO.searchPerson(searchModel);
	}

	public SearchDAO getSearchDAO() {
		return searchDAO;
	}

	public void setSearchDAO(SearchDAO searchDAO) {
		this.searchDAO = searchDAO;
	}

}

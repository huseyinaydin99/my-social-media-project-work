package com.huseyinaydin.dao;

import java.util.List;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.SearchModel;

public interface SearchDAO {
	public List<Person> searchPerson(SearchModel searchModel);
}

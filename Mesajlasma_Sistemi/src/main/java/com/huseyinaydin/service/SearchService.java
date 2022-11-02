package com.huseyinaydin.service;

import java.util.List;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.SearchModel;

public interface SearchService {
	public List<Person> searchPerson(SearchModel searchModel);
}

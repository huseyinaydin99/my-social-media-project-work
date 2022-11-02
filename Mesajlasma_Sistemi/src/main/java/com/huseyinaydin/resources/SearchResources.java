package com.huseyinaydin.resources;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.model.SearchModel;
import com.huseyinaydin.service.SearchService;

@ManagedBean(name = "searchResources")
@RequestScoped
public class SearchResources {
	
	@Autowired
	@Qualifier("searchService")
	private SearchService searchService;
	
	private List<Person> persons;
	
	public void initPersons(SearchModel searchModel){
		this.persons = this.searchService.searchPerson(searchModel);
		System.out.println("size " + this.persons.size());
		for(int i = 0; i < this.persons.size(); i++){
			System.out.println("burda");
			System.out.println(persons.get(i).getPerson_Adi());
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("arama.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Person> getPersons() {
		
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	
}

package com.huseyinaydin.resources;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.huseyinaydin.model.Person;
import com.huseyinaydin.service.PersonService;
import com.huseyinaydin.session.MySession;

@ManagedBean(name = "personResources")
@RequestScoped
public class PersonResources {

	@Autowired
	@Qualifier("personService")
	private PersonService personService;
	
	@Autowired
	@Qualifier("mySession")
	private MySession mySession;

	private List<Person> persons;

	public void initPersons() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameterOne = params.get("profileID");
		try {
			this.persons = this.personService.personKaydiniAl(Long.parseLong(parameterOne));
			System.out.println("şorda " + persons.size());
			for (int i = 0; i < persons.size(); i++) {
				if(persons.get(i).getPerson_Id() == this.mySession.getPerson().getPerson_Id()){
					persons.remove(i);
				}
				System.out.println(persons.get(i).getPerson_Adi());
			}
		} catch (NumberFormatException ex) {

		}
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public List<Person> getPersons() {
		initPersons();
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public MySession getMySession() {
		return mySession;
	}

	public void setMySession(MySession mySession) {
		this.mySession = mySession;
	}

}

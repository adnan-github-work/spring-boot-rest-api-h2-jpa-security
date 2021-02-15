package com.sample.person.service;

import com.sample.person.model.Person;
import com.sample.person.model.PersonList;

public interface PersonService {


    public PersonList getPerson();

    public void createPerson(Person person);

    public void deletePersonById(String id);

    public Person getPersonById(String id);

    public void updatePersonById(String id, Person person);

}

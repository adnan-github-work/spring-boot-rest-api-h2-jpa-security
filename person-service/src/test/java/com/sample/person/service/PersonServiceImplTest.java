package com.sample.person.service;

import com.sample.person.exception.DataNotFoundException;
import com.sample.person.model.Person;
import com.sample.person.model.PersonEntity;
import com.sample.person.model.PersonList;
import com.sample.person.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sample.person.util.Constants.*;
import static com.sample.person.util.Constants.FAV_COLOR;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private PersonEntity personEntity;

    private List<PersonEntity> personEntities;


    @Before
    public void setup() {
        personEntity = new PersonEntity(FIRST_NAME, LAST_NAME, AGE, FAV_COLOR);
        personEntities = new ArrayList<>();
        personEntities.add(personEntity);
    }

    @Test
    public void testGetAllPerson() {

        when(personRepository.findAll()).thenReturn(personEntities);

        PersonList personList = personService.getPerson();

        assertFalse(personList.getPerson().isEmpty());

        assertEquals(FIRST_NAME, personList.getPerson().get(0).getFirstName());
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetPersonNoDataFound() {
        personService.getPerson();
    }

    @Test
    public void testGetPersonById() {

        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.ofNullable(personEntity));

        Person person = personService.getPersonById(PERSON_ID);

        assertNotNull(person);

        assertEquals(FIRST_NAME, person.getFirstName());
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetPersonByIdNoDataFound() {
        personService.getPersonById(PERSON_ID);
    }


}
package com.sample.person.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sample.person.model.Person;
import com.sample.person.model.PersonList;
import com.sample.person.service.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static com.sample.person.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRestControllerTest {

    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private PersonRestController personRestController;


    private Person person;

    private PersonList personList;


    @Before
    public void setup() {

        person = new Person.Builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withAge(AGE)
                .withFavouriteColor(FAV_COLOR)
                .build();
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        personList = new PersonList.Builder().setPerson(people).build();
    }

    @Test
    public void testGetPerson() throws JsonProcessingException {

        when(personService.getPerson()).thenReturn(personList);

        ResponseEntity responseEntity = personRestController.getAllPerson();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(personList.toJsonString(),objectToJsonString(responseEntity.getBody()));

    }


    @Test
    public void testGetPersonById() throws JsonProcessingException {

        when(personService.getPersonById(PERSON_ID)).thenReturn(person);

        ResponseEntity responseEntity = personRestController.getPersonById(PERSON_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(person.toJsonString(),objectToJsonString(responseEntity.getBody()));
    }

    @Test
    public void testCreatePerson() {
        ResponseEntity responseEntity = personRestController.createPerson(person);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testCreatePersonBadRequest() {
        ResponseEntity responseEntity = personRestController.createPerson(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdatePersonById() {
        ResponseEntity responseEntity = personRestController.updatePersonById(PERSON_ID, person);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdatePersonByIdBadRequest() {
        ResponseEntity responseEntity = personRestController.updatePersonById(null, null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePersonById() {
        ResponseEntity responseEntity = personRestController.deletePersonById(PERSON_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePersonIdBadRequest() {
        ResponseEntity responseEntity = personRestController.deletePersonById("");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    public static String objectToJsonString(Object object) {
        if (object != null) {
            try {
                ObjectMapper jsonMapper = new ObjectMapper();
                jsonMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
                jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
                return jsonMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                //LOGGER.error("Failed to parse object " + object.getClass(), e);
            }
        }
        return "";
    }

}

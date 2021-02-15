package com.sample.person.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.person.Application;
import com.sample.person.model.Person;
import com.sample.person.model.PersonList;
import com.sample.person.model.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.sample.person.util.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonApiIntegrationTest {

    private Person person;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;

    private String newAccessToken;

    @Before
    public void before() throws Exception {
        person = new Person.Builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withAge(AGE)
                .withFavouriteColor(FAV_COLOR)
                .build();
        accessToken = generateAccessTokenFromUser("TestUser1", "TestPassword1");
        newAccessToken = generateAccessTokenFromUser("TestUser2", "TestPassword2");

    }

    @Test
    public void testGetAllPerson() throws Exception {

        mockMvc.perform(post(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isCreated());

        mockMvc.perform(get(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.person[0].first_name", is(FIRST_NAME)));

    }

    @Test
    public void testGetAllPersonNoData() throws Exception {
        mockMvc.perform(get(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonById() throws Exception {

        mockMvc.perform(post(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isCreated());

        List<Person> found = getPersonsCall().getPerson();
        String id = found.get(0).getId();
        mockMvc.perform(get(PERSON_API + id).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.first_name", is(FIRST_NAME)));

    }

    @Test
    public void testGetPersonByIdNoData() throws Exception {
        mockMvc.perform(get(PERSON_API +"1").header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePerson() throws Exception {

        mockMvc.perform(post(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isCreated());
        List<Person> found = getPersonsCall().getPerson();
        assertThat(found).extracting(Person::getFirstName).containsOnly(FIRST_NAME);
    }


    @Test
    public void testUpdatePersonById() throws Exception {

        mockMvc.perform(post(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isCreated());

        PersonList Persons = getPersonsCall();
        person = new Person.Builder(person).withFavouriteColor(UPDATE_FAV_COLOR).build();

        mockMvc.perform(put(PERSON_API + Persons.getPerson().get(0).getId()).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isOk());
        List<Person> found = getPersonsCall().getPerson();
        assertThat(found).extracting(Person::getFavouriteColour).containsOnly(UPDATE_FAV_COLOR);
    }

    @Test
    public void testDeletePersonById() throws Exception {

        mockMvc.perform(post(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON).content(person.toJsonString())).andExpect(status().isCreated());

        List<Person> found = getPersonsCall().getPerson();
        String id = found.get(0).getId();

        mockMvc.perform(delete(PERSON_API + id).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        mockMvc.perform(get(PERSON_API +id).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test
    public void testDeletePersonByIdForNoMatchingData() throws Exception {
        mockMvc.perform(delete(PERSON_API + "1").header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePersonByIdForEmptyId() throws Exception {
        mockMvc.perform(delete(PERSON_API + "").header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());
    }

    private PersonList getPersonsCall() throws Exception {
        MvcResult result = mockMvc.perform(get(PERSON_API).header(AUTHORIZATION, accessToken).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        return objectMapper.readValue(response, PersonList.class);
    }

    private String generateAccessTokenFromUser(String userName, String password) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userName);
        userEntity.setPassword(password);
        mockMvc.perform(post("/api/users/sign-up").contentType(MediaType.APPLICATION_JSON).content(userEntity.toJsonString())).andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(userEntity.toJsonString())).andExpect(status().isOk()).andReturn();
        return mvcResult.getResponse().getHeader(AUTHORIZATION);
    }
}

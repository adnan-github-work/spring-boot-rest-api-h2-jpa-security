package com.sample.person.service;

import com.sample.person.exception.DataNotFoundException;
import com.sample.person.model.Person;
import com.sample.person.model.PersonEntity;
import com.sample.person.model.PersonList;
import com.sample.person.model.UserEntity;
import com.sample.person.repository.PersonRepository;
import com.sample.person.repository.UserRepository;
import com.sample.person.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sample.person.utils.ServiceUtils.*;

@Service
public class PersonServiceImpl implements PersonService {

    static Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Override
    public PersonList getPerson() {
        List<Person> personList = convertToPersonDtoList(personRepository.findAll());
        PersonList people = new PersonList.Builder().setPerson(personList).build();
        if (people.getPerson().isEmpty()) {
            log.info("No person found in database");
            throw new DataNotFoundException(Constants.ResponseMessage.DATA_NOT_FOUND);
        }
        log.info("Retrieved all person data {}", people);
        return people;
    }

    @Override
    public void createPerson(Person person) {
        UserEntity userEntity = getCurrentUser();
        PersonEntity personEntity = personDTOToPersonEntity(person);

        log.info("Creating person by user id - {}", userEntity.getId());
        personRepository.save(personEntity);
    }

    @Override
    public Person getPersonById(String id) {
        PersonEntity personEntity = getPersonEntityById(id);
        return personEntityToPersonDTO(personEntity);
    }

    @Override
    public void deletePersonById(String id) {
        getPersonEntityById(id);

        log.info("Deleting person for id - {}", id);
        personRepository.deleteById(id);
    }

    @Override
    public void updatePersonById(String id, Person person) {
        getPersonEntityById(id);
        PersonEntity personEntity = personDTOToPersonEntity(person);
        personEntity.setId(id);
        log.info("Updating person for id - {}", id);
        personRepository.save(personEntity);

    }


    private PersonEntity getPersonEntityById(String id) {
        log.info("Getting person by id: {}", id);
        return personRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constants.ResponseMessage.DATA_NOT_FOUND));
    }

    private UserEntity getCurrentUser() {
        String userName = httpServletRequest.getUserPrincipal().getName();
        log.info("Current user : {}", userName);
        return userRepository.findByUsername(userName);
    }

}

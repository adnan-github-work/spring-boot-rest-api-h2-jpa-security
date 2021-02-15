package com.sample.person.controller;

import com.sample.person.model.Person;
import com.sample.person.service.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.sample.person.utils.Constants.ResponseMessage.*;
import static com.sample.person.utils.ServiceUtils.validateInput;

@RestController
public class PersonRestController implements PersonApi {


    Logger log = LoggerFactory.getLogger(PersonRestController.class);

    @Autowired
    private PersonServiceImpl personService;

    @Override
    public ResponseEntity<Object> getAllPerson() {
        log.info("Inside PersonRestController getAllPerson");
        return new ResponseEntity<>(personService.getPerson(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getPersonById( @NotNull @NotEmpty String id) {
        log.info("Inside PersonRestController getPersonById");
        if (!validateInput(id)) {
            log.info(REQUEST_BODY_VALIDATION_FAILED);
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(personService.getPersonById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createPerson(@Valid Person body) {
        log.info("Inside PersonRestController createPerson");
        if (!validateInput(body)) {
            log.info(REQUEST_BODY_VALIDATION_FAILED);
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        personService.createPerson(body);
        return new ResponseEntity<>(SUCCESS_CREATE ,HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Object> updatePersonById(@NotNull @NotEmpty String id, @Valid Person body) {
        log.info("Inside PersonRestController updatePersonById");
        if (!validateInput(id) && !validateInput(body)) {
            log.info(REQUEST_BODY_VALIDATION_FAILED);
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        personService.updatePersonById(id, body);
        return new ResponseEntity<>(SUCCESS_UPDATE, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> deletePersonById(@NotNull @NotEmpty String id) {
        log.info("Inside PersonRestController deletePersonById");
        if (!validateInput(id)) {
            log.info(REQUEST_BODY_VALIDATION_FAILED);
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        personService.deletePersonById(id);
        return new ResponseEntity<>(SUCCESS_DELETE, HttpStatus.OK);
    }

}

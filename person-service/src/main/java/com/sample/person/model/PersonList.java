package com.sample.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;
import java.util.List;

public class PersonList implements Serializable{

    public PersonList() {
    }

    @JsonProperty("person")
    private List<Person> person;

    public List<Person> getPerson() {
        return person;
    }

    public PersonList(Builder builder) {
        this.person = builder.personList;
    }

    public static class Builder {

        private List<Person> personList;

        public Builder(){
        }

        public Builder(PersonList persons) {
            this.personList = persons.person;
        }

        public Builder setPerson(List<Person> personList) {
            this.personList = personList;
            return this;
        }

        public PersonList build(){
            return new PersonList(this);
        }
    }

    @Override
    public String toString() {
        return "PersonList{" +
                 person.toString() +
                '}';
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

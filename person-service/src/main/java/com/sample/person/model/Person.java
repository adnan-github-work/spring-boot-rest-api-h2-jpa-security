package com.sample.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;

public class Person implements Serializable {

    public Person() {
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("modified_at")
    private Timestamp modifiedAt;

    @NotEmpty
    @NotNull
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty
    @NotNull
    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @Positive
    @JsonProperty("age")
    private int age;

    @NotEmpty
    @NotNull
    @JsonProperty("favourite_colour")
    private String favouriteColour;

    public String getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getFavouriteColour() {
        return favouriteColour;
    }

    public Person(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.favouriteColour = builder.favouriteColor;
        this.createdAt = builder.createdAt;
        this.modifiedAt = builder.modifiedAt;
    }

    public static class Builder {

        private String id;
        private Timestamp createdAt;
        private Timestamp modifiedAt;
        private String firstName;
        private int age;
        private String lastName;
        private String favouriteColor;

        public Builder(){

        }

        public Builder(Person person){
            this.id = person.id;
            this.firstName = person.firstName;
            this.lastName = person.lastName;
            this.age = person.age;
            this.favouriteColor = person.favouriteColour;
            this.createdAt = person.createdAt;
            this.modifiedAt = person.modifiedAt;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withModifiedAt(Timestamp modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withFavouriteColor(String favouriteColor) {
            this.favouriteColor = favouriteColor;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

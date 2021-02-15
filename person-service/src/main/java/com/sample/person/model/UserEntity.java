package com.sample.person.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "user")
public class UserEntity implements Serializable {
    public UserEntity() {

    }

    @Id
    @GeneratedValue(generator = "custom-generator",
            strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "custom-generator",
            strategy = "com.sample.person.utils.BaseIdentifierGenerator")
    protected String id;

    @Column(name = "username")
    @NotEmpty
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotEmpty
    @NotBlank
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}
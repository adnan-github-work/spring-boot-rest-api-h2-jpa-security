package com.sample.person.exception;

import java.util.Date;

public class ServiceErrorResponse {

    private Date timestamp;
    private String person;
    private String details;

    static ServiceErrorResponse serviceErrorResponseBuilder = new ServiceErrorResponse();

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPerson() {
        return person;
    }

    public String getDetails() {
        return details;
    }

    ServiceErrorResponse withTimestamp(Date timestamp){
        this.timestamp = timestamp;
        return this;
    }

    ServiceErrorResponse withPerson(String person){
        this.person = person;
        return this;
    }

    ServiceErrorResponse withDetails(String details){
        this.details = details;
        return this;
    }

}



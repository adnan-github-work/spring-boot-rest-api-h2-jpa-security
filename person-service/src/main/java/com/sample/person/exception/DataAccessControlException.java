package com.sample.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class DataAccessControlException extends RuntimeException {
    public DataAccessControlException(String person) {
        super(person);
    }
}

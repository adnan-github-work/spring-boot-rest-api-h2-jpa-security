package com.sample.person.exception;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;

import static com.sample.person.exception.ValidationErrorResponse.INVALID_RESPONSE;

@RestControllerAdvice
@RestController
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ServiceErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ServiceErrorResponse exceptionResponse = ServiceErrorResponse.serviceErrorResponseBuilder.withTimestamp(new Date()).withPerson(ex.getMessage()).withDetails(request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ValidationErrorResponse> onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new ValidationErrorResponse.Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        if (INVALID_RESPONSE.equalsIgnoreCase(e.getMessage()))
            return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException exception,
    HttpHeaders headers,
    HttpStatus status, WebRequest request) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new ValidationErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ServiceErrorResponse> onEmptyResultDataAccessException(
            EmptyResultDataAccessException e, WebRequest request) {
        ServiceErrorResponse errorResponse = ServiceErrorResponse.serviceErrorResponseBuilder.withTimestamp(new Date()).withPerson(e.getMessage()).withDetails(request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessControlException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ServiceErrorResponse> onPersonNotFoundException(
            DataAccessControlException e, WebRequest request) {
        ServiceErrorResponse errorResponse = ServiceErrorResponse.serviceErrorResponseBuilder.withTimestamp(new Date()).withPerson(e.getMessage()).withDetails(request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ServiceErrorResponse> onPersonNotFoundException(
            DataNotFoundException e, WebRequest request) {
        ServiceErrorResponse errorResponse = ServiceErrorResponse.serviceErrorResponseBuilder.withTimestamp(new Date()).withPerson(e.getMessage()).withDetails(request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

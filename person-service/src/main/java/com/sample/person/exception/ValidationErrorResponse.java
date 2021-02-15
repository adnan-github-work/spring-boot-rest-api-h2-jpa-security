package com.sample.person.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    public static final String INVALID_RESPONSE = "invalid_response";

    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }

    public static class Violation {

        private final String fieldName;

        private final String person;

        public Violation(String fieldName, String person) {
            this.fieldName = fieldName;
            this.person = person;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getPerson() {
            return person;
        }
    }
}



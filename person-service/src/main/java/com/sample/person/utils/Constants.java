package com.sample.person.utils;

public class Constants {

    static final String UTILITY_CLASS = "Utility class";

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static class PersonAPI {

        private PersonAPI() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String API = "/api";
        public static final String PERSON_URL = "/person";
        public static final String PERSON_BY_ID_URL = "/person/{id}";
        public static final String APPLICATION_JSON = "application/json";
        public static final String BEARER_KEY = "bearer-key";
        public static final String PATCH_PARAM_ID = "id";
    }

    public static class ResponseMessage {

        private ResponseMessage() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String DATA_NOT_FOUND = "Data not found";
        public static final String BAD_REQUEST = "Bad request";
        public static final String SUCCESS_CREATE = "Successfully created data";
        public static final String SUCCESS_UPDATE = "Successfully updated data";
        public static final String SUCCESS_DELETE = "Successfully deleted data";
        public static final String REQUEST_BODY_VALIDATION_FAILED = "Request body validation failed";
    }

    public static class SecurityConstants {

        private SecurityConstants() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String SECRET = "SecretKeyToGenJWTs";
        public static final long EXPIRATION_TIME = 864_000_000; // 10 days
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final String SIGN_UP_URL = "/api/users/sign-up";
    }

}

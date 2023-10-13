package com.diego.loansystem.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.NoHandlerFoundException;

public class InvalidURLException extends NoHandlerFoundException {
    public InvalidURLException(String httpMethod, String requestURL, HttpHeaders headers) {
        super(httpMethod, requestURL, headers);
    }
}

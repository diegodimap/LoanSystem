package com.diego.loansystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidLoanTypeException.class)
    public ResponseEntity<ApplicationError> handlerInvalidLoanType(InvalidLoanTypeException invalidLoanTypeException){
        ApplicationError error = new ApplicationError();
        error.setErrorCode(400);
        error.setErrorMessage(invalidLoanTypeException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoanDurationException.class)
    public ResponseEntity<ApplicationError> handlerInvalidLoanDuration(InvalidLoanDurationException invalidLoanDurationException){
        ApplicationError error = new ApplicationError();
        error.setErrorCode(400);
        error.setErrorMessage(invalidLoanDurationException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPayloadException.class)
    public ResponseEntity<ApplicationError> handlerInvalidPayloadException(InvalidPayloadException invalidPayloadException){
        ApplicationError error = new ApplicationError();
        error.setErrorCode(400);
        error.setErrorMessage(invalidPayloadException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoanAmountException.class)
    public ResponseEntity<ApplicationError> handlerInvalidPayloadException(InvalidLoanAmountException invalidLoanAmountException){
        ApplicationError error = new ApplicationError();
        error.setErrorCode(400);
        error.setErrorMessage(invalidLoanAmountException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidURLException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApplicationError> handleNoHandlerFoundException (){
        ApplicationError error = new ApplicationError();
        error.setErrorCode(404);
        error.setErrorMessage("This URL is not available. Try http://localhost:8080/fees or http://localhost:8080/installments");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

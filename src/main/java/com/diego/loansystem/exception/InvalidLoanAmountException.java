package com.diego.loansystem.exception;

public class InvalidLoanAmountException extends Exception{
    public InvalidLoanAmountException(String message) {
        super(message);
    }
}

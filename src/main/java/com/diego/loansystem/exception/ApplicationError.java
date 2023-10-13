package com.diego.loansystem.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationError {
    private int errorCode;
    private String errorMessage;
}

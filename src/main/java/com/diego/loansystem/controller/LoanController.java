package com.diego.loansystem.controller;

import com.diego.loansystem.exception.InvalidLoanAmountException;
import com.diego.loansystem.exception.InvalidLoanDurationException;
import com.diego.loansystem.exception.InvalidLoanTypeException;
import com.diego.loansystem.exception.InvalidPayloadException;
import com.diego.loansystem.service.LoanService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoanController {


    LoanService loanService = new LoanService();

    @PostMapping("/fees")
    public String calculateLoanFees(@RequestBody JsonNode loanData) throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        return loanService.calculateLoanFees(loanData);
    }

    @PostMapping("/installments")
    public String calculateLoanInstallments(@RequestBody JsonNode loanData) throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        return loanService.calculateLoanInstallments(loanData);
    }


}

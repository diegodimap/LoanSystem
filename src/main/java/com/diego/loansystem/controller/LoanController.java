package com.diego.loansystem.controller;

import com.diego.loansystem.model.Loan;
import com.diego.loansystem.service.CalculateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoanController {


    CalculateService calculateService = new CalculateService();

    @PostMapping("/fees")
    public String calculateLoanFees(@RequestBody JsonNode loanData) throws JsonProcessingException {
        return calculateService.calculateLoanFees(loanData);
    }

    @PostMapping("/installments")
    public String calculateLoanInstallments(@RequestBody JsonNode loanData) throws JsonProcessingException {
        return calculateService.calculateLoanInstallments(loanData);
    }


}

package com.diego.loansystem.service;

import com.diego.loansystem.model.Loan;

public class CalculateService {

    //must return projected fees
    public String calculateLoanFees(Loan loan){

        return "fees";
    }

    //must return projected installments
    public String calculateLoanInstallments(Loan loan){

        return "installments";
    }
}

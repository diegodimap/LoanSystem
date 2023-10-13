package com.diego.loansystem.util;

import com.diego.loansystem.model.Loan;

public class LoanCreator {
    public static Loan createValidWeeklyLoan(){
        Loan loan = new Loan();
        loan.setAmount(3000);
        loan.setDuration(3);
        loan.setType("weekly");
        return loan;
    }

    public static Loan createValidMonthlyLoan(){
        Loan loan = new Loan();
        loan.setAmount(1000);
        loan.setDuration(5);
        loan.setType("monthly");
        return loan;
    }

    public static Loan createInvalidWeeklyLoanDuration(){
        Loan loan = new Loan();
        loan.setAmount(1000);
        loan.setDuration(5);
        loan.setType("weekly");
        return loan;
    }

    public static Loan createInvalidMontlhyLoanDuration(){
        Loan loan = new Loan();
        loan.setAmount(1000);
        loan.setDuration(30);
        loan.setType("monthly");
        return loan;
    }

    public static Loan createInvalidLoanAmount(){
        Loan loan = new Loan();
        loan.setAmount(0);
        loan.setDuration(10);
        loan.setType("monthly");
        return loan;
    }

    public static Loan createInvalidLoanType(){
        Loan loan = new Loan();
        loan.setAmount(1000);
        loan.setDuration(5);
        loan.setType("yearly");
        return loan;
    }
}

package com.diego.loansystem.service;

import com.diego.loansystem.exception.InvalidLoanAmountException;
import com.diego.loansystem.exception.InvalidLoanDurationException;
import com.diego.loansystem.exception.InvalidLoanTypeException;
import com.diego.loansystem.exception.InvalidPayloadException;
import com.diego.loansystem.model.Loan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanService {

    ObjectMapper objectMapper = new ObjectMapper();

    //must return projected fees
    public String calculateLoanFees(JsonNode loan) throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        return calculateFeesOrInstallments(loan, "fees");
    }

    //must return projected installments
    public String calculateLoanInstallments(JsonNode loan) throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        return calculateFeesOrInstallments(loan, "installments");
    }

    private String calculateFeesOrInstallments(JsonNode loanData, String option) throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        Loan loan = null;
        try {
            loan = objectMapper.readValue(loanData.toString(), Loan.class);
        } catch (JsonProcessingException e) {
                throw new InvalidPayloadException("Something is wrong with the payload. Make sure it has loan amount, loan duration, and loan type. Ex.: {" +
                        "   'amount': 1000," +
                        "   'duration': 12," +
                        "   'type': 'monthly'" +
                        "}");
        }

        if(loan.getAmount() <= 0){
            throw new InvalidLoanAmountException("Loan amount must be a positive number.");
        }

        StringBuilder answer = new StringBuilder();
        LocalDate currentDate = LocalDate.now();
        double installment = loan.getAmount() / loan.getDuration();

        //1% per week + 0.5% of the principal (cap in $50) every 2 weeks
        if(loan.getType().equals("weekly")){
            //first installment in a week
            currentDate = currentDate.plus(1, ChronoUnit.WEEKS);

            //maximum number of weeks is 4.
            if(loan.getDuration() > 4 || loan.getDuration() < 1){
                throw new InvalidLoanDurationException("Invalid duration for this type of loan. Weekly loans must be from 1 to 4 weeks.");
            }

            double totalFee = loan.getAmount() * (0.01*loan.getDuration());
            double weeklyFee = totalFee/loan.getDuration();
            double biWeeklyFee = loan.getAmount() * 0.005;


            //biweekly fee cap to $50
            if(biWeeklyFee > 50){
                biWeeklyFee = 50;
            }

            double biweeklyTotalInstallment = 0;
            for(int i=1; i<=loan.getDuration(); i++){
                if(option.equals("fees")) {
                    answer.append(dateFormat(currentDate) + " => " + weeklyFee + "\n");
                }else{
                    if(i%2==0) {
                        biweeklyTotalInstallment = installment + weeklyFee;
                    }else{
                        answer.append(dateFormat(currentDate) + " => " + (installment + weeklyFee) + "\n");
                    }
                }
                //every 2 weeks, 0.5% of the principal (cap in $50) is added as a fee
                if(i%2==0){
                    if(option.equals("fees")) {
                        answer.append(dateFormat(currentDate) + " => " + biWeeklyFee + "\n");
                    }else{
                        answer.append(dateFormat(currentDate) + " => " + (biweeklyTotalInstallment+biWeeklyFee) + "\n");
                    }
                }

                currentDate = currentDate.plus(1, ChronoUnit.WEEKS);
            }
        }else if(loan.getType().equals("monthly")){
            //first installment in a month
            currentDate = currentDate.plus(1, ChronoUnit.MONTHS);

            //maximum number of months is 12.
            if(loan.getDuration() > 12 || loan.getDuration() < 1){
                throw new InvalidLoanDurationException("Invalid duration for this type of loan. Monthly loans must be from 1 to 12 months.");
            }

            double totalFee = loan.getAmount() * (0.04*loan.getDuration());
            double monthlyFee = totalFee/loan.getDuration();
            double quarterFee = loan.getAmount() * 0.005;

            //quarter fee cap to $100
            if(quarterFee > 100){
                quarterFee = 50;
            }

            double quarterTotalInstallment = 0;
            for(int i=1; i<=loan.getDuration(); i++){
                if(option.equals("fees")) {
                    answer.append(dateFormat(currentDate) + " => " + monthlyFee + "\n");
                }else{
                    if(i%3==0) {
                        quarterTotalInstallment = installment + monthlyFee;
                    }else{
                        answer.append(dateFormat(currentDate) + " => " + (installment + monthlyFee) + "\n");
                    }
                }

                //every 3 months, 0.5% of the principal (cap in $100) is added as a fee
                if(i%3==0){
                    if(option.equals("fees")) {
                        answer.append(dateFormat(currentDate) + " => " + quarterFee + "\n");
                    }else{
                        answer.append(dateFormat(currentDate) + " => " + (quarterTotalInstallment+quarterFee) + "\n");
                    }
                }

                currentDate = currentDate.plus(1, ChronoUnit.MONTHS);
            }
        }else{
            throw new InvalidLoanTypeException("Invalid loan type. The allowed types are 'weekly' and 'monthly'.");
        }

        return answer.toString();
    }

    private String dateFormat(LocalDate localDate){
        return String.format("%02d", localDate.getDayOfMonth()) + "/" +
                String.format("%02d", localDate.getMonthValue()) + "/" +
                localDate.getYear();
    }
}

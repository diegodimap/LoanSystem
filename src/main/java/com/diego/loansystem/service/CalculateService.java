package com.diego.loansystem.service;

import com.diego.loansystem.model.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculateService {

    //must return projected fees
    public String calculateLoanFees(Loan loan){
        return calculateFeesOrInstallments(loan, "fees");
    }

    //must return projected installments
    public String calculateLoanInstallments(Loan loan){
        return calculateFeesOrInstallments(loan, "installments");
    }

    private String calculateFeesOrInstallments(Loan loan, String option){
        String answer = "";
        LocalDate currentDate = LocalDate.now();
        double installment = loan.getAmount() / loan.getDuration();

        //1% per week + 0.5% of the principal (cap in $50) every 2 weeks
        if(loan.getType().equals("weekly")){
            //first installment in a week
            currentDate = currentDate = currentDate.plus(1, ChronoUnit.WEEKS);

            //maximum number of weeks is 4.
            if(loan.getDuration() > 4){
                loan.setDuration(4);
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
                    answer += dateFormat(currentDate) + " => " + weeklyFee + "\n";
                }else{
                    if(i%2==0) {
                        biweeklyTotalInstallment = installment + weeklyFee;
                    }else{
                        answer += dateFormat(currentDate) + " => " + (installment + weeklyFee) + "\n";
                    }
                }
                //every 2 weeks, 0.5% of the principal (cap in $50) is added as a fee
                if(i%2==0){
                    if(option.equals("fees")) {
                        answer += dateFormat(currentDate) + " => " + biWeeklyFee + "\n";
                    }else{
                        answer += dateFormat(currentDate) + " => " + (biweeklyTotalInstallment+biWeeklyFee) + "\n";
                    }
                }

                currentDate = currentDate.plus(1, ChronoUnit.WEEKS);
            }
        }else if(loan.getType().equals("monthly")){
            //first installment in a month
            currentDate = currentDate.plus(1, ChronoUnit.MONTHS);

            //maximum number of months is 12.
            if(loan.getDuration() > 12){
                loan.setDuration(12);
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
                    answer += dateFormat(currentDate) + " => " + monthlyFee + "\n";
                }else{
                    if(i%3==0) {
                        quarterTotalInstallment = installment + monthlyFee;
                    }else{
                        answer += dateFormat(currentDate) + " => " + (installment + monthlyFee) + "\n";
                    }
                }

                //every 3 months, 0.5% of the principal (cap in $100) is added as a fee
                if(i%3==0){
                    if(option.equals("fees")) {
                        answer += dateFormat(currentDate) + " => " + quarterFee + "\n";
                    }else{
                        answer += dateFormat(currentDate) + " => " + (quarterTotalInstallment+quarterFee) + "\n";
                    }
                }

                currentDate = currentDate.plus(1, ChronoUnit.MONTHS);
            }
        }else{
            try {
                throw new Exception("Invalid loan type");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return answer;
    }

    private String dateFormat(LocalDate localDate){
        return String.format("%02d", localDate.getDayOfMonth()) + "/" +
                String.format("%02d", localDate.getMonthValue()) + "/" +
                localDate.getYear();
    }
}

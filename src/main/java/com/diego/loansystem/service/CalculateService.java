package com.diego.loansystem.service;

import com.diego.loansystem.model.Loan;

public class CalculateService {

    //must return projected fees
    public String calculateLoanFees(Loan loan){
        String answer = "";

        //1% per week + 0.5% of the principal (cap in $50) every 2 weeks
        if(loan.getType().equals("weekly")){
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

            for(int i=1; i<=loan.getDuration(); i++){

                answer += "Fee week " + i + " = " + weeklyFee + "\n";

                //every 2 weeks, 0.5% of the principal (cap in $50) is added as a fee
                if(i%2==0){
                    answer += "Fee week " + i + " = " + biWeeklyFee + "\n";
                }
            }
        }else if(loan.getType().equals("monthly")){
            //maximum number of weeks is 4.
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

            for(int i=1; i<=loan.getDuration(); i++){

                answer += "Fee month " + i + " = " + monthlyFee + "\n";

                //every 3 months, 0.5% of the principal (cap in $100) is added as a fee
                if(i%3==0){
                    answer += "Fee month " + i + " = " + quarterFee + "\n";
                }
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

    //must return projected installments
    public String calculateLoanInstallments(Loan loan){

        return "installments";
    }
}

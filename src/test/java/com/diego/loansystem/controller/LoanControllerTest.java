package com.diego.loansystem.controller;

import com.diego.loansystem.exception.InvalidLoanAmountException;
import com.diego.loansystem.exception.InvalidLoanDurationException;
import com.diego.loansystem.exception.InvalidLoanTypeException;
import com.diego.loansystem.exception.InvalidPayloadException;
import com.diego.loansystem.model.Loan;
import com.diego.loansystem.util.DateFormatter;
import com.diego.loansystem.util.LoanCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class LoanControllerTest {

    @Autowired
    private LoanController loanController;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCalculateLoanWeeklyFees_WhenSuccessful() throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        Loan loan = LoanCreator.createValidWeeklyLoan();
        JsonNode node = objectMapper.valueToTree(loan);

        String date1 = DateFormatter.dateFormat(LocalDate.now().plus(1, ChronoUnit.WEEKS));
        String date2 = DateFormatter.dateFormat(LocalDate.now().plus(2, ChronoUnit.WEEKS));
        String date3 = date2;
        String date4 = DateFormatter.dateFormat(LocalDate.now().plus(3, ChronoUnit.WEEKS));

        String expectedAnswer =
                date1 + " => 30.0\n" +
                date2 + " => 30.0\n" +
                date3 + " => 15.0\n" +
                date4 + " => 30.0\n";

        String answer = loanController.calculateLoanFees(node);

        Assertions.assertThat(answer).isEqualTo(expectedAnswer);
    }

    @Test
    void testCalculateLoanMonthlyFees_WhenSuccessful() throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        Loan loan = LoanCreator.createValidMonthlyLoan();
        JsonNode node = objectMapper.valueToTree(loan);

        String date1 = DateFormatter.dateFormat(LocalDate.now().plus(1, ChronoUnit.MONTHS));
        String date2 = DateFormatter.dateFormat(LocalDate.now().plus(2, ChronoUnit.MONTHS));
        String date3 = DateFormatter.dateFormat(LocalDate.now().plus(3, ChronoUnit.MONTHS));
        String date4 = date3;
        String date5 = DateFormatter.dateFormat(LocalDate.now().plus(4, ChronoUnit.MONTHS));
        String date6 = DateFormatter.dateFormat(LocalDate.now().plus(5, ChronoUnit.MONTHS));

        String expectedAnswer =
                date1 + " => 40.0\n" +
                date2 + " => 40.0\n" +
                date3 + " => 40.0\n" +
                date4 + " => 5.0\n" +
                date5 + " => 40.0\n" +
                date6 + " => 40.0\n";

        String answer = loanController.calculateLoanFees(node);

        Assertions.assertThat(answer).isEqualTo(expectedAnswer);
    }

    @Test
    void testCalculateWeeklyLoanInstallments_WhenSuccessful() throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        Loan loan = LoanCreator.createValidWeeklyLoan();
        JsonNode node = objectMapper.valueToTree(loan);

        String date1 = DateFormatter.dateFormat(LocalDate.now().plus(1, ChronoUnit.WEEKS));
        String date2 = DateFormatter.dateFormat(LocalDate.now().plus(2, ChronoUnit.WEEKS));
        String date3 = DateFormatter.dateFormat(LocalDate.now().plus(3, ChronoUnit.WEEKS));

        String expectedAnswer =
                date1 + " => 1030.0\n" +
                        date2 + " => 1045.0\n" +
                        date3 + " => 1030.0\n";

        String answer = loanController.calculateLoanInstallments(node);

        Assertions.assertThat(answer).isEqualTo(expectedAnswer);
    }

    @Test
    void testCalculateMonthlyLoanInstallments_WhenSuccessful() throws InvalidLoanDurationException, InvalidLoanTypeException, InvalidPayloadException, InvalidLoanAmountException {
        Loan loan = LoanCreator.createValidMonthlyLoan();
        JsonNode node = objectMapper.valueToTree(loan);

        String date1 = DateFormatter.dateFormat(LocalDate.now().plus(1, ChronoUnit.MONTHS));
        String date2 = DateFormatter.dateFormat(LocalDate.now().plus(2, ChronoUnit.MONTHS));
        String date3 = DateFormatter.dateFormat(LocalDate.now().plus(3, ChronoUnit.MONTHS));
        String date4 = DateFormatter.dateFormat(LocalDate.now().plus(4, ChronoUnit.MONTHS));
        String date5 = DateFormatter.dateFormat(LocalDate.now().plus(5, ChronoUnit.MONTHS));

        String expectedAnswer =
                date1 + " => 240.0\n" +
                        date2 + " => 240.0\n" +
                        date3 + " => 245.0\n" +
                        date4 + " => 240.0\n" +
                        date5 + " => 240.0\n";

        String answer = loanController.calculateLoanInstallments(node);

        Assertions.assertThat(answer).isEqualTo(expectedAnswer);
    }

    @Test
    void testCalculateLoanFees_InvalidWeeklyLoanDuration(){
        Loan loan = LoanCreator.createInvalidWeeklyLoanDuration();
        JsonNode node = objectMapper.valueToTree(loan);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidLoanDurationException.class, () -> {
            loanController.calculateLoanFees(node);
        });

        String expectedMessage = "Invalid duration for this type of loan. Weekly loans must be from 1 to 4 weeks.";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCalculateLoanFees_InvalidMonthlyLoanDuration(){
        Loan loan = LoanCreator.createInvalidMontlhyLoanDuration();
        JsonNode node = objectMapper.valueToTree(loan);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidLoanDurationException.class, () -> {
            loanController.calculateLoanFees(node);
        });

        String expectedMessage = "Invalid duration for this type of loan. Monthly loans must be from 1 to 12 months.";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCalculateLoanFees_InvalidLoanType(){
        Loan loan = LoanCreator.createInvalidLoanType();
        JsonNode node = objectMapper.valueToTree(loan);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidLoanTypeException.class, () -> {
            loanController.calculateLoanFees(node);
        });

        String expectedMessage = "Invalid loan type. The allowed types are 'weekly' and 'monthly'.";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCalculateLoanFees_InvalidLoanAmount(){
        Loan loan = LoanCreator.createInvalidLoanAmount();
        JsonNode node = objectMapper.valueToTree(loan);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidLoanAmountException.class, () -> {
            loanController.calculateLoanFees(node);
        });

        String expectedMessage = "Loan amount must be a positive number.";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
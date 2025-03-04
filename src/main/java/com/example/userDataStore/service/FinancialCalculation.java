package com.example.userDataStore.service;

import com.example.userDataStore.entity.InvestmentEntity;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class FinancialCalculation {

    //----------------------------Financial Summary Calculations ----------------------------

    // Calculate Total Investment for User
    public double calculateTotalInvestment(UsersEntity user) {
        return user.getInvestmentsList().stream()
                .mapToDouble(InvestmentEntity::getInvestedAmount)
                .sum();
    }

    // Calculate Total Loan Amount for User
    public double calculateTotalLoan(UsersEntity user){
        return user.getLoansList().stream()
                .mapToDouble(LoanEntity::getLoanAmount)
                .sum();
    }

    // Calculate Total Payments for User (All Loans)
    public double calculateTotalPaymentForAllLoans(UsersEntity user){
        return user.getLoansList().stream()
                .flatMap(loan -> loan.getPaymentslist()
                        .stream())
                        .mapToDouble(PaymentEntity::getPaymentAmount)
                        .sum();
    }

    // Calculate Total Remaining Loans Balance for User
    public double calculateTotalRemainingLoansBalance(UsersEntity user) {
        return user.getLoansList().stream()
                .mapToDouble(loan -> {
                    double totalPaymentsForLoan = loan.getPaymentslist().stream()
                            .mapToDouble(PaymentEntity::getPaymentAmount)
                            .sum();
                    return loan.getLoanAmount() - totalPaymentsForLoan;
                }).sum();
    }

    // Calculate Total Account Balance for User
    public double calculateTotalAccountBalance(UsersEntity user){
        return calculateTotalInvestment(user) - calculateTotalRemainingLoansBalance(user);
    }

    //----------------------------Loan Calculations ----------------------------
    // Calculate Remaining Loan
    public double calculateRemainingLoan (LoanEntity loan){
        double totalPaid = loan.getPaymentslist().stream()
                .mapToDouble(PaymentEntity::getPaymentAmount)
                .sum();
        return loan.getLoanAmount() - totalPaid;
    }

    // Calculate Monthly Payment
    public double calculateMonthlyPayment(double loanAmount, int months){
        //double interestRate = 0.05;    // Assume 5% annual interest
        //double monthlyRate = interestRate / 12;
        //return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
        return loanAmount / months;
    }

    // Calculate Total Payment for loan
    public double calculateTotalPayment (LoanEntity loan){
        return loan.getPaymentslist().stream()
                .mapToDouble(PaymentEntity::getPaymentAmount)
                .sum();
    }

    /**
     * Since the remaining months until loan completion based on the start date and duration
     */
    public int calculateMonthRemaining(LoanEntity loan){
        LocalDate today = LocalDate.now();
        LocalDate loanEndDate = loan.getStartDate().plusMonths(loan.getDurationMonths());

        return Period.between(today, loanEndDate).getMonths();
    }
}
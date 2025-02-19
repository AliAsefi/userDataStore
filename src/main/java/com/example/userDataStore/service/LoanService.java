package com.example.userDataStore.service;

import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.LoanRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LoanRepository loanRepository;

    @Transactional  // Ensures the database transaction is committed
    public LoanDTO createLoan(LoanDTO loanDTO){

        UsersEntity usersEntity = usersRepository.findById(loanDTO.getUsersId())
                .orElseThrow(()->new RuntimeException("User not found!"));

        LoanEntity loanEntity = mapper.mapLoanDtoToLoanEntity(loanDTO,usersEntity);

        double monthlyPayment = calculateMonthlyPayment(loanDTO.getLoanAmount(),loanDTO.getDurationMonths());
        loanEntity.setMonthlyPayment(monthlyPayment);

        // ✅ FIX: Initialize `remainingBalance` with loan amount
        loanEntity.setRemainingBalance(loanDTO.getLoanAmount());

        usersEntity.setTotalLoan(usersEntity.getTotalLoan() + loanDTO.getLoanAmount());
        usersRepository.save(usersEntity);
        LoanEntity loan = loanRepository.save(loanEntity);

        return mapper.mapLoanEntityToLoanDto(loan);
    }

    /**
     * Since the remaining months until loan completion based on the start date and duration
     */
    public int calculateMonthRemaining(LoanEntity loan){
        LocalDate today = LocalDate.now();
        LocalDate loanEndDate = loan.getStartDate().plusMonths(loan.getDurationMonths());

        return Period.between(today, loanEndDate).getMonths();
    }

    public double calculateMonthlyPayment(double loanAmount, int months){
        //double interestRate = 0.05;    // Assume 5% annual interest
        //double monthlyRate = interestRate / 12;
        //return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
        return loanAmount / months;
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(mapper::mapLoanEntityToLoanDto)
                .collect(Collectors.toList());
    }

    public LoanDTO getLoanById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Loan not found!"));

        return mapper.mapLoanEntityToLoanDto(loanEntity);
    }

    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setLoanAmount(loanDTO.getLoanAmount());
        loan.setDurationMonths(loanDTO.getDurationMonths());

        loanRepository.save(loan);
        return mapper.mapLoanEntityToLoanDto(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}

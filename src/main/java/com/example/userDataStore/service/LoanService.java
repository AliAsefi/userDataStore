package com.example.userDataStore.service;

import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.LoanRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private FinancialCalculation financialCalculation;

    @Transactional  // Ensures the database transaction is committed
    public LoanDTO createLoan(LoanDTO loanDTO){

        UsersEntity usersEntity = usersRepository.findById(loanDTO.getUsersId())
                .orElseThrow(()->new RuntimeException("User not found!"));

        LoanEntity loanEntity = mapper.mapLoanDtoToLoanEntity(loanDTO,usersEntity);

        double monthlyPayment = financialCalculation.calculateMonthlyPayment(loanEntity.getLoanAmount(), loanEntity.getDurationMonths());
        loanEntity.setMonthlyPayment(monthlyPayment);

        double remainingLoan = financialCalculation.calculateRemainingLoan(loanEntity);
        loanDTO.setRemainingLoan(remainingLoan);

        double totalPayment = financialCalculation.calculateTotalPayment(loanEntity);
        loanDTO.setTotalPayment(totalPayment);

        usersRepository.save(usersEntity);
        LoanEntity loan = loanRepository.save(loanEntity);

        return mapper.mapLoanEntityToLoanDto(loan);
    }

    public LoanDTO getLoanById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Loan not found!"));

        return mapper.mapLoanEntityToLoanDto(loanEntity);
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(mapper::mapLoanEntityToLoanDto)
                .collect(Collectors.toList());
    }

    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setLoanAmount(loanDTO.getLoanAmount());
        loan.setDurationMonths(loanDTO.getDurationMonths());
        loan.setMonthlyPayment(financialCalculation.calculateMonthlyPayment(loan.getLoanAmount(), loan.getDurationMonths()));

        loanRepository.save(loan);
        return mapper.mapLoanEntityToLoanDto(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public List<LoanDTO> getLoanByUserId(Long id) {
        List<LoanEntity> loanEntities = loanRepository.findByUsers_Id(id);
        return loanEntities
                .stream()
                .map(mapper::mapLoanEntityToLoanDto)
                .collect(Collectors.toList());
    }
}

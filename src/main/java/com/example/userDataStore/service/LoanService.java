package com.example.userDataStore.service;

import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.LoanRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        double monthlyPayment = calculateMonthlyPayment(loanDTO.getLoanAmount(),loanDTO.getDurationMonths());

        LoanEntity loanEntity = mapper.mapLoanDtoToLoanEntity(loanDTO,usersEntity);

        usersEntity.setTotalLoan(usersEntity.getTotalLoan() + loanDTO.getLoanAmount());
        usersRepository.save(usersEntity);
        loanRepository.save(loanEntity);

        return mapper.mapLoanEntityToLoanDto(loanEntity);
    }

    public double calculateMonthlyPayment(double loanAmount, int months){
        //double interestRate = 0.05;    // Assume 5% annual interest
        //double monthlyRate = interestRate / 12;
        //return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
        return loanAmount / months;
    }
}

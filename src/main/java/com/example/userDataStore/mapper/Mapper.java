package com.example.userDataStore.mapper;

import com.example.userDataStore.dto.InvestmentDTO;
import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.dto.PaymentDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.InvestmentEntity;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    // Convert UserEntity to UserDTO
    public UsersDTO mapUserEntityToDto(UsersEntity usersEntity){
        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setId(usersEntity.getId());
        usersDTO.setFirstname(usersEntity.getFirstname());
        usersDTO.setLastname(usersEntity.getLastname());
        usersDTO.setTotalInvestment(usersEntity.getTotalInvestment());
        usersDTO.setTotalLoan(usersEntity.getTotalLoan());

        return usersDTO;
    }

    // Convert UserDTO to UserEntity
    public UsersEntity mapUserDtoToUserEntity(UsersDTO usersDTO){
        UsersEntity usersEntity = new UsersEntity();

        usersEntity.setId(usersDTO.getId());
        usersEntity.setFirstname(usersDTO.getFirstname());
        usersEntity.setLastname(usersDTO.getLastname());
        usersEntity.setTotalInvestment(usersDTO.getTotalInvestment());
        usersEntity.setTotalLoan(usersDTO.getTotalLoan());

        return usersEntity;
    }

    //convert InvestmentEntity to InvestmentDTO
    public InvestmentDTO mapInvestmentEntityToInvestmentDto(InvestmentEntity investmentEntity){
        return new InvestmentDTO(
                investmentEntity.getId(),
                investmentEntity.getUsers().getId(),
                investmentEntity.getInvestedAmount(),
                investmentEntity.getInvestedDate()
        );
    }

    //convert InvestmentDTO to InvestmentEntity
    public InvestmentEntity mapInvestmentDtoToInvestmentEntity(InvestmentDTO investmentDTO, UsersEntity usersEntity){
        InvestmentEntity investmentEntity = new InvestmentEntity();

        investmentEntity.setUsers(usersEntity);
        investmentEntity.setInvestedAmount(investmentDTO.getInvestedAmount());
        investmentEntity.setInvestedDate(investmentDTO.getInvestedDate());

        return investmentEntity;
    }

    //convert LoanEntity to LoanDTO
    public LoanDTO mapLoanEntityToLoanDto(LoanEntity loanEntity){
        LoanDTO loanDTO = new LoanDTO();

        //loanDTO.setId(loanEntity.getId());
        loanDTO.setUsersId(loanEntity.getUsers().getId());
        loanDTO.setLoanAmount(loanEntity.getLoanAmount());
        loanDTO.setStartDate(loanEntity.getStartDate());
        loanDTO.setDurationMonths(loanEntity.getDurationMonths());
        loanDTO.setMonthlyPayment(loanEntity.getMonthlyPayment());
        loanDTO.setRemainingBalance(loanEntity.getRemainingBalance());

        return loanDTO;
    }

    //convert LoanDTO to LoanEntity
    public LoanEntity mapLoanDtoToLoanEntity(LoanDTO loanDTO , UsersEntity usersEntity){
        LoanEntity loanEntity = new LoanEntity();

        //loanEntity.setId(loanDTO.getId());
        loanEntity.setUsers(usersEntity);
        loanEntity.setLoanAmount(loanDTO.getLoanAmount());
        loanEntity.setStartDate(loanDTO.getStartDate());
        loanEntity.setDurationMonths(loanDTO.getDurationMonths());
        loanEntity.setMonthlyPayment(loanDTO.getMonthlyPayment());
        loanEntity.setRemainingBalance(loanDTO.getRemainingBalance());

        return loanEntity;
    }

    //convert PaymentEntity to PaymentDTO
    public PaymentDTO mapPaymentEntityToPaymentDto(PaymentEntity paymentEntity){
        PaymentDTO paymentDTO = new PaymentDTO();

        //paymentDTO.setId(paymentEntity.getId());
        paymentDTO.setLoanId(paymentEntity.getLoans().getId());
        paymentDTO.setPaymentAmount(paymentEntity.getPaymentAmount());
        paymentDTO.setPaymentDate(paymentEntity.getPaymentDate());

        return paymentDTO;
    }

    //convert PaymentDTO to PaymentEntity
    public PaymentEntity mapPaymentDtoToPaymentEntity(PaymentDTO paymentDTO , LoanEntity loanEntity){
        PaymentEntity paymentEntity = new PaymentEntity();

        //paymentEntity.setId(paymentDTO.getId());
        paymentEntity.setLoans(loanEntity);
        paymentEntity.setPaymentAmount(paymentDTO.getPaymentAmount());
        paymentEntity.setPaymentDate(paymentDTO.getPaymentDate());

        return paymentEntity;
    }
}

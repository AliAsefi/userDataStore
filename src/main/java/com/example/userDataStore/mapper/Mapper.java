package com.example.userDataStore.mapper;

import com.example.userDataStore.dto.*;
import com.example.userDataStore.entity.InvestmentEntity;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.service.FinancialCalculation;
import com.example.userDataStore.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private FinancialCalculation financialCalculation;
    //-------------------- User ---------------------------------

    // Convert UserEntity to UserDTO
    public UsersDTO mapUserEntityToDto(UsersEntity usersEntity){
        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setId(usersEntity.getId());
        usersDTO.setFirstname(usersEntity.getFirstname());
        usersDTO.setLastname(usersEntity.getLastname());

        List<InvestmentDTO> investmentDTOList = mapInvestmentEntityListToInvestmentDTOList(usersEntity.getInvestmentsList());
        usersDTO.setInvestmentsList(investmentDTOList);

        List<LoanDTO> loanDTOList = mapLoanEntityListToLoanDTOList(usersEntity.getLoansList());
        usersDTO.setLoansList(loanDTOList);

        return usersDTO;
    }

    // Map List<UsersEntity> to List<UserDTO>
    public List<UsersDTO> mapUsersEntityListToUserDTOList(List<UsersEntity> usersEntities){
        return usersEntities.stream()
                .map(user -> mapUserEntityToDto(user))
                .collect(Collectors.toList());
    }


    // Convert UserDTO to UserEntity
    public UsersEntity mapUserDtoToUserEntity(UsersDTO usersDTO){
        UsersEntity usersEntity = new UsersEntity();

        usersEntity.setId(usersDTO.getId());
        usersEntity.setFirstname(usersDTO.getFirstname());
        usersEntity.setLastname(usersDTO.getLastname());

        List<InvestmentEntity> investmentEntityList = mapInvestmentDTOListToInvestmentEntityList(usersDTO.getInvestmentsList(),usersEntity);
        usersEntity.setInvestmentsList(investmentEntityList);

        List<LoanEntity> loanEntityList = mapLoanDtoListToLoanEntityList(usersDTO.getLoansList(),usersEntity);
        usersEntity.setLoansList(loanEntityList);

        return usersEntity;
    }

    // Map List<UsersDTO> to List<UsersEntity>
    public List<UsersEntity> mapUserDTOListToUsersEntityList(List<UsersDTO> usersDTOS){
        return usersDTOS.stream()
                .map(user -> mapUserDtoToUserEntity(user))
                .collect(Collectors.toList());
    }


    //-------------------- Investment ---------------------------------

    //convert InvestmentEntity to InvestmentDTO
    public InvestmentDTO mapInvestmentEntityToInvestmentDto(InvestmentEntity investmentEntity){
        InvestmentDTO investmentDTO = new InvestmentDTO();

        investmentDTO.setId(investmentEntity.getId());
        investmentDTO.setUsersId(investmentEntity.getUsers().getId());
        investmentDTO.setInvestedAmount(investmentEntity.getInvestedAmount());
        investmentDTO.setInvestedDate(investmentEntity.getInvestedDate());

        return investmentDTO;
    }

    // Map List<InvestmentEntity> to List<InvestmentDTO>
    public List<InvestmentDTO> mapInvestmentEntityListToInvestmentDTOList(List<InvestmentEntity> investmentEntities){
        return investmentEntities.stream()
                .map(investment -> mapInvestmentEntityToInvestmentDto(investment))
                .collect(Collectors.toList());
    }


    //convert InvestmentDTO to InvestmentEntity
    public InvestmentEntity mapInvestmentDtoToInvestmentEntity(InvestmentDTO investmentDTO, UsersEntity usersEntity){
        InvestmentEntity investmentEntity = new InvestmentEntity();

        investmentEntity.setUsers(usersEntity);
        investmentEntity.setInvestedAmount(investmentDTO.getInvestedAmount());
        investmentEntity.setInvestedDate(investmentDTO.getInvestedDate());

        return investmentEntity;
    }

    // Map List<InvestmentDTO> to List<InvestmentEntity>
    public List<InvestmentEntity> mapInvestmentDTOListToInvestmentEntityList(List<InvestmentDTO> investmentDTOList, UsersEntity user){
        return investmentDTOList.stream()
                .map(investment -> mapInvestmentDtoToInvestmentEntity(investment , user))
                .collect(Collectors.toList());
    }


    //-------------------- Loan ---------------------------------

    //convert LoanEntity to LoanDTO
    public LoanDTO mapLoanEntityToLoanDto(LoanEntity loanEntity){
        LoanDTO loanDTO = new LoanDTO();

        loanDTO.setId(loanEntity.getId());
        loanDTO.setUsersId(loanEntity.getUsers().getId());
        loanDTO.setLoanAmount(loanEntity.getLoanAmount());
        loanDTO.setStartDate(loanEntity.getStartDate());
        loanDTO.setDurationMonths(loanEntity.getDurationMonths());
        loanDTO.setMonthlyPayment(loanEntity.getMonthlyPayment());


        double remainingLoan = financialCalculation.calculateRemainingLoan(loanEntity);
        loanDTO.setRemainingLoan(remainingLoan);

        double totalPayment = financialCalculation.calculateTotalPayment(loanEntity);
        loanDTO.setTotalPayment(totalPayment);

        List<PaymentDTO> paymentDTOList = mapPaymentEntityListToPaymentDTOList(loanEntity.getPaymentslist());
        loanDTO.setPaymentslist(paymentDTOList);

        return loanDTO;
    }

    // Map List<LoanEntity> to List<LoanDTO>
    public List<LoanDTO> mapLoanEntityListToLoanDTOList(List<LoanEntity> loanEntityList){
        return loanEntityList.stream()
                .map(loan -> mapLoanEntityToLoanDto(loan))
                .collect(Collectors.toList());
    }

    //convert LoanDTO to LoanEntity
    public LoanEntity mapLoanDtoToLoanEntity(LoanDTO loanDTO , UsersEntity usersEntity){
        LoanEntity loanEntity = new LoanEntity();

        loanEntity.setId(loanDTO.getId());
        loanEntity.setUsers(usersEntity);
        loanEntity.setLoanAmount(loanDTO.getLoanAmount());
        loanEntity.setStartDate(loanDTO.getStartDate());
        loanEntity.setDurationMonths(loanDTO.getDurationMonths());
        loanEntity.setMonthlyPayment(loanDTO.getMonthlyPayment());

        return loanEntity;
    }

    // Map List<LoanDTO> to List<LoanEntity>
    public List<LoanEntity> mapLoanDtoListToLoanEntityList(List<LoanDTO> loanDTOList, UsersEntity user){
        return loanDTOList.stream()
                .map(loan -> mapLoanDtoToLoanEntity(loan , user))
                .collect(Collectors.toList());
    }


    //-------------------- Payment ---------------------------------

    //convert PaymentEntity to PaymentDTO
    public PaymentDTO mapPaymentEntityToPaymentDto(PaymentEntity paymentEntity){
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(paymentEntity.getId());
        paymentDTO.setLoanId(paymentEntity.getLoans().getId());
        paymentDTO.setPaymentAmount(paymentEntity.getPaymentAmount());
        paymentDTO.setPaymentDate(paymentEntity.getPaymentDate());

        return paymentDTO;
    }

    // Map List<PaymentEntity> to List<PaymentDTO>
    public List<PaymentDTO> mapPaymentEntityListToPaymentDTOList(List<PaymentEntity> paymentEntities){
        return paymentEntities.stream()
                .map(payment -> mapPaymentEntityToPaymentDto(payment))
                .collect(Collectors.toList());
    }

    //convert PaymentDTO to PaymentEntity
    public PaymentEntity mapPaymentDtoToPaymentEntity(PaymentDTO paymentDTO , LoanEntity loanEntity){
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setId(paymentDTO.getId());
        paymentEntity.setLoans(loanEntity);
        paymentEntity.setPaymentAmount(paymentDTO.getPaymentAmount());
        paymentEntity.setPaymentDate(paymentDTO.getPaymentDate());

        return paymentEntity;
    }

    // Map List<PaymentDTO> to List<PaymentEntity>
    public List<PaymentEntity> mapPaymentDtoListToPaymentEntityList(List<PaymentDTO> paymentDTO, LoanEntity loan){
        return paymentDTO.stream()
                .map(payment -> mapPaymentDtoToPaymentEntity(payment , loan))
                .collect(Collectors.toList());
    }


    //-------------------- UserFinancial ---------------------------------

    //convert Entity to UsersFinancialSummaryDTO
    public UsersFinancialSummaryDTO mapEntityToUsersFinancialSummaryDTO(UsersEntity usersEntity){
        UsersFinancialSummaryDTO userSummaryDto = new UsersFinancialSummaryDTO();

        userSummaryDto.setUserId(usersEntity.getId());
        userSummaryDto.setUsername(usersEntity.getFirstname() +" "+ usersEntity.getLastname());

//        List<InvestmentDTO> investmentDTOList = mapInvestmentEntityListToInvestmentDTOList(usersEntity.getInvestmentsList());
//        userSummaryDto.setInvestmentsList(investmentDTOList);
//
//        List<LoanDTO> loanDTOList = mapLoanEntityListToLoanDTOList(usersEntity.getLoansList());
//        userSummaryDto.setLoansList(loanDTOList);

        return userSummaryDto;
    }
}

    /*
        Explanation:
        Individual Mappings:

        mapLoanDtoToLoanEntity() → Maps one LoanDTO to LoanEntity.
        mapInvestmentDtoToInvestmentEntity() → Maps one InvestmentDTO to InvestmentEntity.
        List Mappings:

        mapLoanDtoListToLoanEntityList() → Maps a list of LoanDTOs to LoanEntitys.
        mapInvestmentDtoListToInvestmentEntityList() → Maps a list of InvestmentDTOs to InvestmentEntitys.
        Complete User Mapping:

        mapUserDtoToUserEntity():
        Maps basic fields (firstname, lastname).
        Calls list mappers to map loansList and investmentsList.
        Sets the user reference in each LoanEntity and InvestmentEntity to maintain the relationship.
     */
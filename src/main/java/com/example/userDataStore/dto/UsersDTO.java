package com.example.userDataStore.dto;

import com.example.userDataStore.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long id;
    private String firstname;
    private String lastname;

    private List<InvestmentDTO> investmentsList = new ArrayList<>();
    private List<LoanDTO> loansList = new ArrayList<>();
}

/**
 * = new ArrayList<>();
 * If investmentsList and loansList are empty, create a basic user.
 * If they are provided, map and save them as well.
 * private List<InvestmentDTO> investmentsList = new ArrayList<>();
 * private List<LoanDTO> loansList = new ArrayList<>();
 *
 *
 * 3. Update mapUserDtoToUserEntity() in Mapper Class:
 * Add checks to only map investmentsList and loansList if they are provided.
 *
 * public UsersEntity mapUserDtoToUserEntity(UsersDTO usersDTO){
 *     UsersEntity usersEntity = new UsersEntity();
 *     usersEntity.setId(usersDTO.getId());
 *     usersEntity.setFirstname(usersDTO.getFirstname());
 *     usersEntity.setLastname(usersDTO.getLastname());
 *
 *     // Only map investments if provided
 *     if (usersDTO.getInvestmentsList() != null) {
 *         List<InvestmentEntity> investmentEntityList = mapInvestmentDTOListToInvestmentEntityList(usersDTO.getInvestmentsList(), usersEntity);
 *         usersEntity.setInvestmentsList(investmentEntityList);
 *     }
 *
 *     // Only map loans if provided
 *     if (usersDTO.getLoansList() != null) {
 *         List<LoanEntity> loanEntityList = mapLoanDtoListToLoanEntityList(usersDTO.getLoansList(), usersEntity);
 *         usersEntity.setLoansList(loanEntityList);
 *     }
 *
 *     return usersEntity;
 * }
 */
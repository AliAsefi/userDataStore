package com.example.userDataStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersFinancialSummaryDTO {
    private Long userId;
    private String username;

//    private List<LoanDTO> loansList;
//    private List<InvestmentDTO> investmentsList;
    /**
     Calculated Field: totalInvestment
     */
    private Double totalInvestments;
    /**
     Calculated Field:
     */
    private Double totalLoans;
    /**
     Calculated Field: (totalLoans - totalPayment)
     */
    private Double totalRemainingLoansBalance;
    /**
     Calculated Field: (totalInvestment + totalRemainingLoansBalance)
     */
    private Double totalAccountsBalance;
}

/*
     Why Not Use UserDTO?
    ❌ UserDTO contains multiple user details (id, email, etc.), but in UserSummaryDTO, we only need the username.
    ✅ Using String userName keeps UserSummaryDTO cleaner and more efficient.

    private UsersDTO username;

    we can set username in service (firstname + lastname)
    private String username

 */
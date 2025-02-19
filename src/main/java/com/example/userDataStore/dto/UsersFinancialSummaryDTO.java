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
    private Double totalInvestment;
    private Double totalLoan;
    private Double remainingLoanBalance;
    private int remainingLoanMonths;
    private Double totalAccountBalance;
}

/*
     Why Not Use UserDTO?
    ❌ UserDTO contains multiple user details (id, email, etc.), but in UserSummaryDTO, we only need the username.
    ✅ Using String userName keeps UserSummaryDTO cleaner and more efficient.

    private UsersDTO username;

    we can set username in service (firstname + lastname)
    private String username

 */
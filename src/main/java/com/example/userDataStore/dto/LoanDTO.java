package com.example.userDataStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private Long usersId;
    private Double loanAmount;
    private LocalDate startDate;
    private Integer durationMonths;
    private Double monthlyPayment;
    private Double remainingBalance;
}

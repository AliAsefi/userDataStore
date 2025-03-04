package com.example.userDataStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    /**
     Calculated Field: (loanAmount/durationMonths)
     */
    private Double monthlyPayment;
    /**
     Calculated Field: for each loan (loanAmount - totalPayment)
     */
    private Double remainingLoan;   //
    /**
     Calculated Field: Sum of payments for this loan
     */
    private Double totalPayment;

    private List<PaymentDTO> paymentslist = new ArrayList<>();
}

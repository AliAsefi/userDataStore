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
public class InvestmentDTO {
    private Long id;
    private Long usersId;
    private Double investedAmount;
    private LocalDate investedDate;
}

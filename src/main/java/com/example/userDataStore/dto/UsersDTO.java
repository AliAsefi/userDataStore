package com.example.userDataStore.dto;

import com.example.userDataStore.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private Double totalInvestment;
    private Double totalLoan;
}

package com.example.userDataStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown fields
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;
    private String lastname;

    private Double totalInvestment = 0.0;
    private Double totalLoan = 0.0;

    @OneToMany(mappedBy = "users" , cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<InvestmentEntity> investmentsList = new ArrayList<>();

    @OneToMany(mappedBy = "users" , cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<LoanEntity> loansList = new ArrayList<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(Double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public Double getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(Double totalLoan) {
        this.totalLoan = totalLoan;
    }

    public List<InvestmentEntity> getInvestmentsList() {
        return investmentsList;
    }

    public void setInvestmentsList(List<InvestmentEntity> investmentsList) {
        this.investmentsList = investmentsList;
    }

    public List<LoanEntity> getLoansList() {
        return loansList;
    }

    public void setLoansList(List<LoanEntity> loansList) {
        this.loansList = loansList;
    }
}


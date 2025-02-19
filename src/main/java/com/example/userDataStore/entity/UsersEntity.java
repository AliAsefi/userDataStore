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
}


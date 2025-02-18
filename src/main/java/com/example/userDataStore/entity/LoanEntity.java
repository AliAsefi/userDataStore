package com.example.userDataStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
@Table(name = "loans")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UsersEntity users;

    private Double loanAmount;
    private LocalDate startDate;
    private Integer durationMonths;
    private Double monthlyPayment;
    private Double remainingBalance;

    @OneToMany(mappedBy = "loans", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PaymentEntity> paymentslist = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanEntity loanEntity = (LoanEntity) o;
        return Objects.equals(id, loanEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

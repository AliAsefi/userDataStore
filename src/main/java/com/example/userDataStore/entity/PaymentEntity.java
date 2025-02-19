package com.example.userDataStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown fields
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private Double paymentAmount;
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "loans_id")
    private LoanEntity loans;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity paymentEntity = (PaymentEntity) o;
        return Objects.equals(id, paymentEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
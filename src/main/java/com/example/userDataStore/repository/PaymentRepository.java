package com.example.userDataStore.repository;

import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByLoans_Users_Id(Long id);

    List<PaymentEntity> findByLoans_IdAndLoans_Users_Id(Long loanId ,Long userId);

}

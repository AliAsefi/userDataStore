package com.example.userDataStore.repository;

import com.example.userDataStore.entity.InvestmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Long> {

    // ✅ Custom Query to get Investments by userId

    List<InvestmentEntity> findByUsers_Id(Long id);
}

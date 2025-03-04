package com.example.userDataStore.repository;

import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findByUsers_Id(Long id);
}

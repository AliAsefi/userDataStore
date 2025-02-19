package com.example.userDataStore.repository;

import com.example.userDataStore.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    List<UsersEntity> findByFirstnameContainingIgnoreCase(String firstname);
}

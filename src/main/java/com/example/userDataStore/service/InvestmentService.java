package com.example.userDataStore.service;

import com.example.userDataStore.dto.InvestmentDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.InvestmentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.InvestmentRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private UsersRepository usersRepository;

    @Transactional  // Ensures the database transaction is committed
    public InvestmentDTO createInvestment(InvestmentDTO investmentDTO) {

        UsersEntity usersEntity = usersRepository.findById(investmentDTO.getUsersId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        InvestmentEntity investmentEntity = mapper.mapInvestmentDtoToInvestmentEntity(investmentDTO, usersEntity);

        usersEntity.setTotalInvestment(usersEntity.getTotalInvestment() + investmentDTO.getInvestedAmount());
        usersRepository.save(usersEntity);
        investmentRepository.save(investmentEntity);

        return mapper.mapInvestmentEntityToInvestmentDto(investmentEntity);

    }
}

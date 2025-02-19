package com.example.userDataStore.service;

import com.example.userDataStore.dto.InvestmentDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.InvestmentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.InvestmentRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

    public List<InvestmentDTO> getAllInvestments() {
        List<InvestmentEntity> investmentEntity = investmentRepository.findAll();
        return investmentEntity.stream()
                .map(mapper::mapInvestmentEntityToInvestmentDto)
                .collect(Collectors.toList());
    }

    public InvestmentDTO getInvestmentById(Long id) {
        return mapper.mapInvestmentEntityToInvestmentDto(investmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Investment not found!")));
    }

    public InvestmentDTO updateInvestment(Long id, InvestmentDTO investmentDTO) {
        InvestmentEntity investmentEntity = investmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Investment not found"));

        investmentEntity.setInvestedAmount(investmentDTO.getInvestedAmount());
        investmentEntity.setInvestedDate(investmentDTO.getInvestedDate());

        investmentRepository.save(investmentEntity);

        return mapper.mapInvestmentEntityToInvestmentDto(investmentEntity);
    }

    public void deleteInvestment(Long id) {
        investmentRepository.deleteById(id);
    }
}

package com.example.userDataStore.service;

import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.dto.UsersFinancialSummaryDTO;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.UsersRepository;
import com.example.userDataStore.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LoanService loanService;
    @Autowired
    private FinancialCalculation financialCalculation;

    @Transactional  // Ensures the database transaction is committed
    public UsersDTO createUser(UsersDTO userDto){
        UsersEntity usersEntity = mapper.mapUserDtoToUserEntity(userDto);
        usersRepository.save(usersEntity);
        return mapper.mapUserEntityToDto(usersEntity);
    }

    public List<UsersDTO> getAllUsers(){
        return usersRepository.findAll()
                .stream()
                .map(mapper::mapUserEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UsersDTO> findUsersByName(String firstname){
        return usersRepository.findByFirstnameContainingIgnoreCase(firstname)
                .stream()
                .map(mapper::mapUserEntityToDto)
                .collect(Collectors.toList());
    }

    public Optional<UsersDTO> getUserById(Long id){
        return Optional.ofNullable(mapper.mapUserEntityToDto(usersRepository.findById(id).get()));
    }

    public UsersDTO updateUser(Long id, UsersDTO usersDTO) {
        UsersEntity usersEntity = usersRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found!"));
        usersEntity.setFirstname(usersDTO.getFirstname());
        usersEntity.setLastname(usersDTO.getLastname());

        usersRepository.save(usersEntity);
        return mapper.mapUserEntityToDto(usersEntity);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public UsersFinancialSummaryDTO getUserFinancialSummaryById(Long userId){

        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found!"));

        UsersFinancialSummaryDTO userSummaryDto = mapper.mapEntityToUsersFinancialSummaryDTO(usersEntity);
        userSummaryDto.setTotalInvestments(financialCalculation.calculateTotalInvestment(usersEntity));
        userSummaryDto.setTotalLoans(financialCalculation.calculateTotalLoan(usersEntity));
        userSummaryDto.setTotalRemainingLoansBalance(financialCalculation.calculateTotalRemainingLoansBalance(usersEntity));
        userSummaryDto.setTotalAccountsBalance(financialCalculation.calculateTotalAccountBalance(usersEntity));

        return userSummaryDto;
    }

    public List<UsersFinancialSummaryDTO> getAllUserSummaries() {
        List<UsersEntity> users = usersRepository.findAll(); // ✅ Get all users

        return users.stream().map(usersEntity -> {

            UsersFinancialSummaryDTO userSummaryDto = mapper.mapEntityToUsersFinancialSummaryDTO(usersEntity);
            userSummaryDto.setTotalInvestments(financialCalculation.calculateTotalInvestment(usersEntity));
            userSummaryDto.setTotalLoans(financialCalculation.calculateTotalLoan(usersEntity));
            userSummaryDto.setTotalRemainingLoansBalance(financialCalculation.calculateTotalRemainingLoansBalance(usersEntity));
            userSummaryDto.setTotalAccountsBalance(financialCalculation.calculateTotalAccountBalance(usersEntity));

            return userSummaryDto;
        }).collect(Collectors.toList());
    }
}


/**
 * Where should I make calculations:
 *
 * What's Good:
 * Centralized Calculation: You're calculating totalInvestment in one place (InvestmentService), which promotes reuse.
 * Consistent Mapping: The Mapper class handles all conversions between entities and DTOs, maintaining clean architecture.
 * What's Not Ideal:
 * Tight Coupling in Mapper:
 *
 * Your Mapper is tightly coupled with InvestmentService. This creates a circular dependency since both might depend on each other.
 * The Mapper should only handle mapping, not calculations or business logic.
 * Responsibility Confusion:
 *
 * InvestmentService is responsible for CRUD operations, but it also handles calculations.
 * This violates the Single Responsibility Principle.
 * Best Practice: Where to Put Calculations
 * Service Layer:
 *
 * Calculations like totalInvestment, totalLoans, and totalRemainingLoans should be centralized in the UserService, not in InvestmentService or the Mapper.
 * This keeps business logic separate and avoids circular dependencies.
 * Mapper Class:
 *
 * Should only handle Entity ↔ DTO conversions, not calculations.
 * This keeps the Mapper class simple and focused on data transformation.
 */


//    @PostConstruct
//    public void testDatabaseConnection() {
//        System.out.println("Testing database connection...");
//        List<UsersEntity> users = usersRepository.findAll();
//        System.out.println("Users from DB: " + users);
//    }
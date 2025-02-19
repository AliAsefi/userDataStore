package com.example.userDataStore.service;

import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.dto.UsersFinancialSummaryDTO;
import com.example.userDataStore.entity.LoanEntity;
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

        double remainingLoanBalance = usersEntity.getLoansList()
                .stream()
                .mapToDouble(LoanEntity::getRemainingBalance)
                .sum();

        int remainingLoanMonths = usersEntity.getLoansList()
                .stream()
                .mapToInt(loanService::calculateMonthRemaining)
                .max()  // The max remaining months from all loans
                .orElse(0);
        double totalAccountBalance = usersEntity.getTotalInvestment() - remainingLoanBalance;


        UsersFinancialSummaryDTO userSummaryDto = new UsersFinancialSummaryDTO();

        userSummaryDto.setUserId(usersEntity.getId());
        userSummaryDto.setUsername(usersEntity.getFirstname() +" "+ usersEntity.getLastname());
        userSummaryDto.setTotalInvestment(usersEntity.getTotalInvestment());
        userSummaryDto.setTotalLoan(usersEntity.getTotalLoan());
        userSummaryDto.setRemainingLoanBalance(remainingLoanBalance);
        userSummaryDto.setRemainingLoanMonths(remainingLoanMonths);
        userSummaryDto.setTotalAccountBalance(totalAccountBalance);

        return userSummaryDto;
    }

    public List<UsersFinancialSummaryDTO> getAllUserSummaries() {
        List<UsersEntity> users = usersRepository.findAll(); // ✅ Get all users

        return users.stream().map(user -> {

            double remainingLoanBalance = user.getLoansList().stream()
                    .mapToDouble(LoanEntity::getRemainingBalance)
                    .sum();

            int remainingLoanMonths = user.getLoansList().stream()
                    .mapToInt(loanService::calculateMonthRemaining) // ✅ Call LoanService method
                    .max()
                    .orElse(0);

            double totalAccountBalance = user.getTotalInvestment() - remainingLoanBalance;

            UsersFinancialSummaryDTO summaryDTO = new UsersFinancialSummaryDTO();
            summaryDTO.setUserId(user.getId());
            summaryDTO.setUsername(user.getFirstname() +" "+ user.getLastname());
            summaryDTO.setTotalInvestment(user.getTotalInvestment());
            summaryDTO.setRemainingLoanBalance(remainingLoanBalance);
            summaryDTO.setRemainingLoanMonths(remainingLoanMonths);
            summaryDTO.setTotalAccountBalance(totalAccountBalance);

            return summaryDTO;
        }).collect(Collectors.toList());
    }

//    @PostConstruct
//    public void testDatabaseConnection() {
//        System.out.println("Testing database connection...");
//        List<UsersEntity> users = usersRepository.findAll();
//        System.out.println("Users from DB: " + users);
//    }
}

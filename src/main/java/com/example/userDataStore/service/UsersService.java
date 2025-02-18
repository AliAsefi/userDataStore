package com.example.userDataStore.service;

import com.example.userDataStore.dto.UsersDTO;
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

    public List<UsersDTO> findUsersByName(String name){
        return usersRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::mapUserEntityToDto)
                .collect(Collectors.toList());
    }

    public Optional<UsersDTO> getUserById(Long id){
        return Optional.ofNullable(mapper.mapUserEntityToDto(usersRepository.findById(id).get()));
    }





//    @PostConstruct
//    public void testDatabaseConnection() {
//        System.out.println("Testing database connection...");
//        List<UsersEntity> users = usersRepository.findAll();
//        System.out.println("Users from DB: " + users);
//    }
}

package com.example.userDataStore.users;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Transactional  // Ensures the database transaction is committed
    public UsersEntity createUser(UsersEntity user){
        return usersRepository.save(user);
    }

    public List<UsersEntity> getAllUsers(){
        List<UsersEntity> users = usersRepository.findAll();
        System.out.println("Users retrieved: " + users);
        return users;
    }

    public Optional<UsersEntity> getUserById(Long id){
        return usersRepository.findById(id);
    }

//    @PostConstruct
//    public void testDatabaseConnection() {
//        System.out.println("Testing database connection...");
//        List<UsersEntity> users = usersRepository.findAll();
//        System.out.println("Users from DB: " + users);
//    }
}

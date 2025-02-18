package com.example.userDataStore.controller;

import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<UsersDTO> createUsers(@RequestBody UsersDTO usersDTO){
        return new ResponseEntity<>(usersService.createUser(usersDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers(){
        return new ResponseEntity<>(usersService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsersDTO>> getUsersByName(@RequestParam String name){
        return new ResponseEntity<>(usersService.findUsersByName(name),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(usersService.getUserById(id).get(), HttpStatus.OK);
    }

//    @GetMapping("/test1")
//    public String testJSON(){
//        List<UsersEntity> users = usersService.getAllUsers();
//        return users.toString();
//    }

//    @GetMapping("/test2")
//    public String test(){
//        return ("Hallo");
//    }
}

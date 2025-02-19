package com.example.userDataStore.controller;

import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.dto.UsersFinancialSummaryDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(usersService.getUserById(id).get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser (@PathVariable("id") Long id, @RequestBody UsersDTO usersDTO){
        return new ResponseEntity<>(usersService.updateUser(id,usersDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable("id") Long id){
        usersService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsersDTO>> getUsersByName(@RequestParam String firstname){
        return new ResponseEntity<>(usersService.findUsersByName(firstname),HttpStatus.OK);
    }

    @GetMapping("/summary/all")
    public ResponseEntity<List<UsersFinancialSummaryDTO>> getUsersSummary(){
        return new ResponseEntity<>(usersService.getAllUserSummaries(),HttpStatus.OK);
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<UsersFinancialSummaryDTO> getUsersSummaryById(@PathVariable("id") Long id){
        return new ResponseEntity<>(usersService.getUserFinancialSummaryById(id),HttpStatus.OK);
    }


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


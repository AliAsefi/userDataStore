package com.example.userDataStore.users;

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
    public ResponseEntity<UsersEntity> createUsers(@RequestBody UsersEntity user){
        return new ResponseEntity<>(usersService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsersEntity>> getAllUsers(){
        return new ResponseEntity<>(usersService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersEntity> getUserById(@PathVariable("id") Long id){
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

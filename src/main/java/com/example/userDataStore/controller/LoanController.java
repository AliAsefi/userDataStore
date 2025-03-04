package com.example.userDataStore.controller;

import com.example.userDataStore.dto.InvestmentDTO;
import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<LoanDTO> createLoan (@RequestBody LoanDTO loanDTO){
        return new ResponseEntity<>(loanService.createLoan(loanDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(){
        return new ResponseEntity<>(loanService.getAllLoans(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable("id") Long id){
        return new ResponseEntity<>(loanService.getLoanById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan (@PathVariable("id") Long id, @RequestBody LoanDTO loanDTO){
        return new ResponseEntity<>(loanService.updateLoan(id,loanDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan (@PathVariable("id") Long id){
        loanService.deleteLoan(id);
        return ResponseEntity.ok("Loan deleted successfully.");
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<LoanDTO>> getLoanByUserId (@PathVariable("userID") Long id){
        return new ResponseEntity<>(loanService.getLoanByUserId(id), HttpStatus.OK);
    }

}

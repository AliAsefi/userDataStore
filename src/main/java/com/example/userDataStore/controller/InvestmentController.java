package com.example.userDataStore.controller;

import com.example.userDataStore.dto.InvestmentDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @PostMapping("/create")
    public ResponseEntity<InvestmentDTO> createInvestment (@RequestBody InvestmentDTO investmentDTO){
        return new ResponseEntity<>(investmentService.createInvestment(investmentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> getAllInvestments(){
        return new ResponseEntity<>(investmentService.getAllInvestments(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDTO> getInvestmentById(@PathVariable("id") Long id){
        return new ResponseEntity<>(investmentService.getInvestmentById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDTO> updateInvestment (@PathVariable("id") Long id, @RequestBody InvestmentDTO investmentDTO){
        return new ResponseEntity<>(investmentService.updateInvestment(id,investmentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvestment (@PathVariable("id") Long id){
        investmentService.deleteInvestment(id);
        return ResponseEntity.ok("Investment deleted successfully.");
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<InvestmentDTO>> getInvestmentsByUserId (@PathVariable("userID") Long userId){
        return new ResponseEntity<>(investmentService.getInvestmentsByUserId(userId), HttpStatus.OK);
    }
}

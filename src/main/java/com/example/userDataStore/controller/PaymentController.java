package com.example.userDataStore.controller;

import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.dto.PaymentDTO;
import com.example.userDataStore.service.LoanService;
import com.example.userDataStore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> createPayment (@RequestBody PaymentDTO paymentDTO){
        return new ResponseEntity<>(paymentService.createPayment(paymentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments(){
        return new ResponseEntity<>(paymentService.getAllPayments(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("id") Long id){
        return new ResponseEntity<>(paymentService.getPaymentById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment (@PathVariable("id") Long id, @RequestBody PaymentDTO paymentDTO){
        return new ResponseEntity<>(paymentService.updatePayment(id,paymentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment (@PathVariable("id") Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully.");
    }

}

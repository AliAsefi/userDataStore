package com.example.userDataStore.service;

import com.example.userDataStore.dto.LoanDTO;
import com.example.userDataStore.dto.PaymentDTO;
import com.example.userDataStore.dto.UsersDTO;
import com.example.userDataStore.entity.LoanEntity;
import com.example.userDataStore.entity.PaymentEntity;
import com.example.userDataStore.entity.UsersEntity;
import com.example.userDataStore.mapper.Mapper;
import com.example.userDataStore.repository.LoanRepository;
import com.example.userDataStore.repository.PaymentRepository;
import com.example.userDataStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LoanRepository loanRepository;

    @Transactional  // Ensures the database transaction is committed
    public PaymentDTO createPayment(PaymentDTO paymentDTO){

        LoanEntity loanEntity = loanRepository.findById(paymentDTO.getLoanId())
                .orElseThrow(()->new RuntimeException("Loan not found!"));

        PaymentEntity paymentEntity = mapper.mapPaymentDtoToPaymentEntity(paymentDTO,loanEntity);

        loanEntity.setRemainingBalance(loanEntity.getRemainingBalance() - paymentDTO.getPaymentAmount());

        loanRepository.save(loanEntity);
        PaymentEntity payment = paymentRepository.save(paymentEntity);

        return mapper.mapPaymentEntityToPaymentDto(payment);
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(mapper::mapPaymentEntityToPaymentDto)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Long id) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapper.mapPaymentEntityToPaymentDto(payment);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        payment.setPaymentDate(paymentDTO.getPaymentDate());

        paymentRepository.save(payment);
        return mapper.mapPaymentEntityToPaymentDto(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}

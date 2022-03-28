package com.upgrad.paymentservice.repository;

import com.upgrad.paymentservice.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepo extends MongoRepository<Payment,String> {
}

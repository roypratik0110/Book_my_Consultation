package com.upgrad.paymentservice.services;

import com.upgrad.paymentservice.entity.Payment;
import com.upgrad.paymentservice.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    KafkaTemplate<String,Payment> kafkaTemplate;

    @Value("${payment.confirmation.topic}")
    private String paymentConfirmationTopic;

    @Override
    public Payment newPayment(int appointmentId) {
        Payment savePayment = Payment.builder()
                .appointmentId(appointmentId)
                .createDate(LocalDate.now())
                .build();
        paymentRepo.save(savePayment);
        notify(savePayment);
        return savePayment;
    }

    private void notify(Payment payment){
        kafkaTemplate.send(paymentConfirmationTopic, payment);
    }

}

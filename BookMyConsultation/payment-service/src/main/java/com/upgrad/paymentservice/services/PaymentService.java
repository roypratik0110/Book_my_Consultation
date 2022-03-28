package com.upgrad.paymentservice.services;

import com.upgrad.paymentservice.entity.Payment;

public interface PaymentService {

    Payment newPayment(int appointmentId);
}

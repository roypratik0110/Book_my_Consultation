package com.upgrad.paymentservice.controller;

import com.upgrad.paymentservice.dto.PaymentDTO;
import com.upgrad.paymentservice.entity.Payment;
import com.upgrad.paymentservice.services.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ModelMapper modelMapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/payments")
    public ResponseEntity makePayment(@PathParam("appointmentId") int appointmentId){
        Payment savedPayment = paymentService.newPayment(appointmentId);
        PaymentDTO savedPaymentDTO = modelMapper.map(savedPayment,PaymentDTO.class);
        return new ResponseEntity(savedPaymentDTO, HttpStatus.OK);
    }
}

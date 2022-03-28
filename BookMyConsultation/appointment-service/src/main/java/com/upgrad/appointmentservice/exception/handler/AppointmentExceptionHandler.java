package com.upgrad.appointmentservice.exception.handler;

import com.upgrad.appointmentservice.exception.PaymentPendingException;
import com.upgrad.appointmentservice.exception.ResourceUnAvailableException;
import com.upgrad.appointmentservice.exception.SlotUnavailableException;
import com.upgrad.appointmentservice.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentExceptionHandler {

    @ExceptionHandler(SlotUnavailableException.class)
    public ResponseEntity handleSlotUnavailableException(){
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .message("ERR_SLOT_UNAVAILABLE")
                        .errorDetails("Either the slot is already booked or not available")
                        .build());
    }

    @ExceptionHandler(ResourceUnAvailableException.class)
    public ResponseEntity handleResourceUnAvailableException(){
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .message("ERR_INVALID_APPOINTMENT")
                        .errorDetails("The appointment id is invalid")
                        .build());
    }

    @ExceptionHandler(PaymentPendingException.class)
    public ResponseEntity handlePaymentPendingException(){
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .message("ERR_PAYMENT_PENDING")
                        .errorDetails("Prescription cannot be issued since the payment status is pending")
                        .build());
    }
}

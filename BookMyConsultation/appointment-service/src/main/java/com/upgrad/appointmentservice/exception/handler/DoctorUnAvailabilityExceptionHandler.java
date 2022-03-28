package com.upgrad.appointmentservice.exception.handler;

import com.upgrad.appointmentservice.exception.AvailabilityUnAvailableException;
import com.upgrad.appointmentservice.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DoctorUnAvailabilityExceptionHandler {

    @ExceptionHandler(AvailabilityUnAvailableException.class)
    public final ResponseEntity<ErrorResponse> handleAvailabilityUnAvailableException(){
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .message("EMPTY_AVAILABILITY_MAP")
                .errorDetails("Availability not passed in the request")
                .build());
    }
}

package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.model.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public class DoctorClientFallback implements DoctorClient{
    @Override
    public ResponseEntity<Doctor> getDoctor(String authorisation, String id) {
        return null;
    }
}

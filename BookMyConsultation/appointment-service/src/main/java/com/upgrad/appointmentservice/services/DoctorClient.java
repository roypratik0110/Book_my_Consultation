package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.model.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "doctor-service",url = "${doctor-service-url}",fallback = DoctorClientFallback.class)
public interface DoctorClient {

    @GetMapping(path = "/doctors/{id}")
    public ResponseEntity<Doctor> getDoctor(@RequestHeader("Authorization") String authorisation,
                                          @PathVariable("id") String id);
}

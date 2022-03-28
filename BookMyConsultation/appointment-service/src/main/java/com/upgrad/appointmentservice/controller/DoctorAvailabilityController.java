package com.upgrad.appointmentservice.controller;

import com.upgrad.appointmentservice.exception.AvailabilityUnAvailableException;
import com.upgrad.appointmentservice.model.DoctorAvailability;
import com.upgrad.appointmentservice.services.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/availability")
public class DoctorAvailabilityController {

    @Autowired
    DoctorAvailabilityService availabilityService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(path = "/doctor/{id}/availability")
    public ResponseEntity newDoctorAvailability(@PathVariable("id") String id,
                                                @RequestBody DoctorAvailability doctorAvailability)
            throws AvailabilityUnAvailableException {
        doctorAvailability.setDoctorId(id);
        availabilityService.savedDoctorAvailability(doctorAvailability);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/doctor/{id}/availability")
    public ResponseEntity getDoctorAvailabilityById(@PathVariable("id") String id){
        return ResponseEntity.ok(availabilityService.getDoctorAvailability(id));
    }
}

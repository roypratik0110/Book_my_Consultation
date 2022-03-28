package com.upgrad.appointmentservice.controller;

import com.upgrad.appointmentservice.entity.Appointment;
import com.upgrad.appointmentservice.entity.Prescription;
import com.upgrad.appointmentservice.exception.PaymentPendingException;
import com.upgrad.appointmentservice.exception.SlotUnavailableException;
import com.upgrad.appointmentservice.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/appointments")
    public ResponseEntity<Integer> bookAppointment(@RequestBody Appointment appointment) throws SlotUnavailableException {
        return ResponseEntity.ok(appointmentService.appointment(appointment));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("appointmentId") int appointmentId) throws SlotUnavailableException {
        return ResponseEntity.ok(appointmentService.getAppointment(appointmentId));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/appointments")
    public ResponseEntity getAppointmentForUser(@PathVariable("userId") String userId){
        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/prescriptions")
    public ResponseEntity prescription(@RequestBody Prescription prescription) throws PaymentPendingException {
        appointmentService.prescription(prescription);
        return ResponseEntity.ok().build();
    }
}

package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Availability;
import com.upgrad.appointmentservice.exception.AvailabilityUnAvailableException;
import com.upgrad.appointmentservice.model.DoctorAvailability;

import java.util.List;

public interface DoctorAvailabilityService {

    void savedDoctorAvailability(DoctorAvailability availability) throws AvailabilityUnAvailableException;
    DoctorAvailability getDoctorAvailability(String doctorId);
    List<Availability> getAvailabilities(String doctorId);
    Availability updateAvailability(Availability availability);
}

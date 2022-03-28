package com.upgrad.appointmentservice.repositories;

import com.upgrad.appointmentservice.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorAvailabilityRepo extends JpaRepository<Availability,Integer> {

    List<Availability> findByDoctorIdAndAvailabilityDateIn(String doctorId,List<String> availabilityDate);
    List<Availability> findByDoctorIdAndIsBooked(String doctorId, boolean isBooked);
}

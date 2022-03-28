package com.upgrad.appointmentservice.repositories;

import com.upgrad.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByUserId(String userId);
}

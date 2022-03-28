package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Appointment;
import com.upgrad.appointmentservice.entity.Prescription;
import com.upgrad.appointmentservice.exception.PaymentPendingException;
import com.upgrad.appointmentservice.exception.ResourceUnAvailableException;
import com.upgrad.appointmentservice.exception.SlotUnavailableException;

import java.util.List;

public interface AppointmentService {

    int appointment(Appointment appointment) throws SlotUnavailableException;
    Appointment getAppointment(int appointmentId) throws ResourceUnAvailableException;
    List<Appointment> getAppointmentsForUser(String userId);
    void prescription(Prescription prescription) throws PaymentPendingException;
}

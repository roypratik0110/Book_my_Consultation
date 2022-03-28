package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Appointment;
import com.upgrad.appointmentservice.entity.Prescription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    KafkaTemplate<String, Appointment> kafkaTemplate;

    @Autowired
    KafkaTemplate<String,Prescription> kafkaTemplatePrescription;

    @Value("${appointment.notification}")
    private String appointmentConfirmationTopic;

    @Value("prescription.notification")
    private String prescriptionNotification;

    public void notifyAppointmentConfirmation(Appointment appointment){
        log.info(appointment);
        kafkaTemplate.send(appointmentConfirmationTopic,appointment);
    }

    public void notifyPrescription(Prescription prescription) {
        log.info(prescription);
        kafkaTemplatePrescription.send(prescriptionNotification,prescription);
    }
}

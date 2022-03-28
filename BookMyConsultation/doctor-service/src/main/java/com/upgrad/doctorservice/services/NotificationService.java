package com.upgrad.doctorservice.services;

import com.upgrad.doctorservice.entity.Doctor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class NotificationService {

    @Autowired
    SeSEmailVerification emailVerification;

    @Autowired
    KafkaTemplate<String, Doctor> kafkaTemplate;

    @Value("${doctor.registration.notification}")
    private String doctorRegistrationNotificationTopic;

    public void notifyDoctorRegistration(Doctor doctor){
        if(doctor.getStatus().equalsIgnoreCase("Pending")) {
            try {
                emailVerification.sendVerificationEmail(doctor.getEmailId());
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        kafkaTemplate.send(doctorRegistrationNotificationTopic,doctor);
    }
}

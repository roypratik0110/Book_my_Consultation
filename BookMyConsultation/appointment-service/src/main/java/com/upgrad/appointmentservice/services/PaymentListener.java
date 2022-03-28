package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Appointment;
import com.upgrad.appointmentservice.model.PaymentDetails;
import com.upgrad.appointmentservice.repositories.AppointmentRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PaymentListener {

    @Autowired
    AppointmentRepo appointmentRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    NotificationService notificationService;

    @KafkaListener(topics = "${payment.confirmation}", groupId = "${kafka.consumer.payment.groupid}",  containerFactory="paymentKafkaListenerContainerFactory")
    public void listenDoctorRegistration(Message<String> message) throws JsonProcessingException {
        PaymentDetails paymentDetails = mapper.readValue(message.getPayload(),PaymentDetails.class);
        log.info("Payment Details received "+paymentDetails.toString());
        Appointment appointment = null;
        if(paymentDetails.getId()!=null){
            appointment = appointmentRepository.findById(paymentDetails.getAppointmentId()).get();
            appointment.setStatus("Confirmed");
            appointmentRepository.save(appointment);
        }else{
            appointment = appointmentRepository.findById(paymentDetails.getAppointmentId()).get();
            appointment.setStatus("PaymentDeclined");
            appointmentRepository.save(appointment);
        }
        notificationService.notifyAppointmentConfirmation(appointment);
    }
}

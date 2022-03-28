package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Appointment;
import com.upgrad.appointmentservice.entity.Availability;
import com.upgrad.appointmentservice.entity.Prescription;
import com.upgrad.appointmentservice.exception.PaymentPendingException;
import com.upgrad.appointmentservice.exception.ResourceUnAvailableException;
import com.upgrad.appointmentservice.exception.SlotUnavailableException;
import com.upgrad.appointmentservice.model.Doctor;
import com.upgrad.appointmentservice.model.User;
import com.upgrad.appointmentservice.repositories.AppointmentRepo;
import com.upgrad.appointmentservice.repositories.PrescriptionRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    PrescriptionRepo prescriptionRepo;
    @Autowired
    DoctorAvailabilityService availabilityService;
    @Autowired
    UserClient userClient;
    @Autowired
    DoctorClient doctorClient;
    @Autowired
    NotificationService notificationService;

    @Override
    public int appointment(Appointment appointment) throws SlotUnavailableException {
        List<Availability> availabilities = availabilityService.getAvailabilities(appointment.getDoctorId());
        boolean slotFound = false;
        for (Availability availability : availabilities){
            if (availability.getAvailabilityDate().equals(appointment.getAppointmentDate()) &&
            availability.getTimeSlot().equals(appointment.getTimeSlot())){
                if (!availability.isBooked()){
                    availability.setBooked(true);
                    availabilityService.updateAvailability(availability);
                }
                else
                    throw new SlotUnavailableException();
                slotFound = true;
                break;
            }
        }
        if (slotFound){
            appointment.setStatus("PendingPayment");
            User user = getUser(appointment.getUserId());
            Doctor doctor = getDoctor(appointment.getDoctorId());
            appointment.setFirstName(doctor.getFirstName());
            appointment.setLastName(doctor.getLastName());
            appointment.setUserName(user.getFirstName());
            appointment.setEmailId(user.getEmailId());
            appointmentRepo.save(appointment);
            notify(appointment);
        }
        else
            throw new SlotUnavailableException();
        return appointment.getAppointmentId();
    }

    @Override
    public Appointment getAppointment(int appointmentId) throws ResourceUnAvailableException {
        return Optional.ofNullable(
                appointmentRepo.findById(appointmentId)
        ).get().orElseThrow(ResourceUnAvailableException::new);
    }

    @Override
    public List<Appointment> getAppointmentsForUser(String userId) {
        return appointmentRepo.findByUserId(userId);
    }

    @Override
    public void prescription(Prescription prescription) throws PaymentPendingException {
        Appointment appointment = appointmentRepo.getById(prescription.getAppointmentId());
        if (appointment.getStatus().equalsIgnoreCase("Confirmed")) {
            prescription.setFirstName(appointment.getFirstName());
            prescription.setLastName(appointment.getLastName());
            prescription.setPatientName(appointment.getUserName());
            prescription.setEmailId(appointment.getEmailId());
            prescriptionRepo.save(prescription);
            notify(prescription);
        }
        else
            throw new PaymentPendingException();
    }

    private String getAuthorizationToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getCredentials().toString();
    }

    private User getUser(String userId){
        ResponseEntity<User> userEntity = userClient.getUser(getAuthorizationToken(),userId);
        if (userEntity!=null)
            return userEntity.getBody();
        log.error("User Not Found Having UserId "+userId);
        return null;
    }

    private Doctor getDoctor(String id){
        ResponseEntity<Doctor> doctorEntity = doctorClient.getDoctor(getAuthorizationToken(),id);
        if (doctorEntity!=null)
            return doctorEntity.getBody();
        log.error("User Not Found Having UserId "+id);
        return null;
    }

    private void notify(Appointment appointment){
        notificationService.notifyAppointmentConfirmation(appointment);
    }

    private void notify(Prescription prescription){
        notificationService.notifyPrescription(prescription);
    }
}

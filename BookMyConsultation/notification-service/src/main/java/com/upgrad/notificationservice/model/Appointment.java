package com.upgrad.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private int appointmentId;
    private String doctorId;
    private String firstName;
    private String lastName;
    private String userId;
    private String userName;
    private String emailId;
    private String timeSlot;
    private String status;
    private String appointmentDate;
}

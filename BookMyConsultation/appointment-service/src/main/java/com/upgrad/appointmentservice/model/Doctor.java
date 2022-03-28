package com.upgrad.appointmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private String id;
    private String firstName;
    private String lastName;
    private String speciality;
    private LocalDate dob;
    private String emailId;
    private String mobile;
    private String pan;
    private String status;
    private String approvedBy;
    private String approverComments;
    private LocalDate registrationDate;
    private LocalDate verificationDate;
}

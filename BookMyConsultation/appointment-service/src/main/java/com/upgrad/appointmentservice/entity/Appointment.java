package com.upgrad.appointmentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

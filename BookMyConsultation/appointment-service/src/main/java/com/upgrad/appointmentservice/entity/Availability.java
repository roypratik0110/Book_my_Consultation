package com.upgrad.appointmentservice.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String doctorId;
    private String availabilityDate;
    private String timeSlot;
    private boolean isBooked;

}

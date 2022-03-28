package com.upgrad.appointmentservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentDetails {
    private String id;
    private int appointmentId;
    private Date createdDate;
}

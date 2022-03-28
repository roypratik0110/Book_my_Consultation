package com.upgrad.appointmentservice.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ErrorResponse {

    private String message;
    private String errorDetails;
}

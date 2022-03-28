package com.upgrad.appointmentservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medicine {

    private String name;
    private String dosage;
    private String frequency;
    private String remark;
}

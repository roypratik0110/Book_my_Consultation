package com.upgrad.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {

    private String name;
    private String dosage;
    private String frequency;
    private String remark;
}

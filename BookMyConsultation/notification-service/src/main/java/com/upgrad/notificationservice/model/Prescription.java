package com.upgrad.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {

    private String prescriptionId;
    private int appointmentId;
    private String doctorId;
    private String firstName;
    private String lastName;
    private String useId;
    private String patientName;
    private String emailId;
    private String diagnosis;
    private List<Medicine> medicineList;
}

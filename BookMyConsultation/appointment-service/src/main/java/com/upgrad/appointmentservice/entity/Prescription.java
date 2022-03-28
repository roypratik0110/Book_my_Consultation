package com.upgrad.appointmentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Prescription")
public class Prescription {

    @Id
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

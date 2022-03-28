package com.upgrad.doctorservice.services;

import com.upgrad.doctorservice.entity.Doctor;
import com.upgrad.doctorservice.exceptions.RecordNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DoctorService {

    Doctor newDoctor(Doctor doctor);
    Doctor updateDoctor(String id, Doctor doctor,int status) throws RecordNotFoundException;
    List<Doctor> filterByStatusAndSpeciality(String status,String speciality);
    Doctor getById(String id) throws RecordNotFoundException;
    void uploadDocuments(String doctorId, MultipartFile file);
}

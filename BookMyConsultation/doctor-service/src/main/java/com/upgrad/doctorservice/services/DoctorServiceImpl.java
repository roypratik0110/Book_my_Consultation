package com.upgrad.doctorservice.services;

import com.upgrad.doctorservice.entity.Doctor;
import com.upgrad.doctorservice.exceptions.RecordNotFoundException;
import com.upgrad.doctorservice.repository.DoctorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    DoctorRepository doctorRepo;
    @Autowired
    S3Service s3Service;
    @Autowired
    NotificationService notificationService;

    @Override
    public Doctor newDoctor(Doctor doctor) {
        doctor.setRegistrationDate(LocalDate.now());
        if (doctor.getSpeciality() == null)
            doctor.setSpeciality("General Physician");
        if (doctor.getStatus() == null)
            doctor.setStatus("Pending");
        doctorRepo.save(doctor);
        notify(doctor);
        return doctor;
    }

    @Override
    public Doctor updateDoctor(String id, Doctor doctor, int status) throws RecordNotFoundException {
        Doctor getDoctor = doctorRepo.findById(id).orElseThrow(() -> new
                RecordNotFoundException("Requested resource is not available"));
        if (status == 1)
            getDoctor.setStatus("Active");
        else
            getDoctor.setStatus("Rejected");
        getDoctor.setApprovedBy(doctor.getApprovedBy());
        getDoctor.setApproverComments(doctor.getApproverComments());
        getDoctor.setVerificationDate(LocalDate.now());
        doctorRepo.save(getDoctor);
        notify(getDoctor);
        return getDoctor;
    }

    @Override
    public List<Doctor> filterByStatusAndSpeciality(String status, String speciality) {
        if(speciality!=null && !speciality.isEmpty()){
            return doctorRepo.findByStatusAndSpeciality(status,speciality);
        }
        return getActiveDoctorsSortedByRating(status);
    }

    @Override
    public Doctor getById(String id) throws RecordNotFoundException {
        return doctorRepo.findById(id).orElseThrow(() -> new
                RecordNotFoundException("Requested resource is not available"));
    }

    @Override
    public void uploadDocuments(String doctorId, MultipartFile file) {
        try {
            s3Service.uploadFileToS3(doctorId,file);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private void notify(Doctor doctor){
        try {
            notificationService.notifyDoctorRegistration(doctor);
        }catch(Exception s){
            log.error(s.getMessage());
        }
    }

    private List<Doctor> getActiveDoctorsSortedByRating(String status){
        log.info("Fetching doctor list from the database");
        return doctorRepo.findByStatus(status)
                .stream()
                .sorted(new Comparator<Doctor>() {
                    @Override
                    public int compare(Doctor o1, Doctor o2) {
                        if(o1.getRating() == null){
                            o1.setRating(0.0);
                        }
                        if(o2.getRating() == null){
                            o2.setRating(0.0);
                        }
                        return o1.getRating().compareTo(o2.getRating());
                    }
                })
                .limit(20)
                .collect(Collectors.toList());
    }

}

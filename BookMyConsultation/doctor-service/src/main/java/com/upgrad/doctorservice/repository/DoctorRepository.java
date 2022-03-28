package com.upgrad.doctorservice.repository;

import com.upgrad.doctorservice.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

    List<Doctor> findByStatus(String status);
    List<Doctor> findByStatusAndSpeciality(String status,String speciality);
}

package com.upgrad.appointmentservice.repositories;

import com.upgrad.appointmentservice.entity.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrescriptionRepo extends MongoRepository<Prescription,String> {
}

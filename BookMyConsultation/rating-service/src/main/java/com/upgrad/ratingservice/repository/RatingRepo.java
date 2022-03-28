package com.upgrad.ratingservice.repository;

import com.upgrad.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepo extends MongoRepository<Rating, String > {

    List<Rating> findByDoctorId(String doctorId);
}

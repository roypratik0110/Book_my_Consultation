package com.upgrad.ratingservice.services;

import com.upgrad.ratingservice.event.RatingAddEvent;
import com.upgrad.ratingservice.model.Rating;
import com.upgrad.ratingservice.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    RatingRepo ratingRepo;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void submitRating(Rating rating) {
        ratingRepo.save(rating);
        applicationEventPublisher.publishEvent(new RatingAddEvent(rating.getDoctorId()));
    }
}

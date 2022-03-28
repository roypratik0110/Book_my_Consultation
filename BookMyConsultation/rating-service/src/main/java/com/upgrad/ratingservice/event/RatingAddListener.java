package com.upgrad.ratingservice.event;

import com.upgrad.ratingservice.model.AverageRating;
import com.upgrad.ratingservice.model.Rating;
import com.upgrad.ratingservice.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RatingAddListener {

    @Autowired
    RatingRepo ratingRepo;
    @Autowired
    KafkaTemplate<String, AverageRating> kafkaTemplate;

    @Value("${doctor.rating}")
    private String doctorRatingTopic;

    @EventListener
    public void handleAddRatingEvent(RatingAddEvent ratingAddEvent){
        double averageRating = ratingRepo.findByDoctorId(ratingAddEvent.getDoctorId())
                .stream().collect(Collectors.averagingInt(Rating::getRating));
        kafkaTemplate.send(doctorRatingTopic,AverageRating.builder()
                .doctorId(ratingAddEvent.getDoctorId())
                .avgRating(averageRating)
                .build());
    }
}

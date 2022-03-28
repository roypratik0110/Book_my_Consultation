package com.upgrad.ratingservice.controller;

import com.upgrad.ratingservice.model.Rating;
import com.upgrad.ratingservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/ratings")
    public ResponseEntity addRating(@RequestBody Rating rating){
        ratingService.submitRating(rating);
        return ResponseEntity.ok().build();
    }
}

package com.upgrad.ratingservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingAddEvent {

    private String doctorId;
}

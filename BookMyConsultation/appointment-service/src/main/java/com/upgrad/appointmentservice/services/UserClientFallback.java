package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public class UserClientFallback implements UserClient{
    @Override
    public ResponseEntity<User> getUser(String authorisation, String userId) {
        return null;
    }
}

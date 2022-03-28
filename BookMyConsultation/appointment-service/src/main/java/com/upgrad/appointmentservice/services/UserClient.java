package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service",url = "${user-service-url}",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String authorisation,
                                        @PathVariable("userId") String userId);
}

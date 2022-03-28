package com.upgrad.notificationservice.controller;

import com.upgrad.notificationservice.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class EmailVerificationController {

    @Autowired
    EmailService emailService;

    @GetMapping(path = "/verify")
    public ResponseEntity verifyEmailId(@PathParam("emailId") String emailId){
        emailService.verifyEmail(emailId);
        return ResponseEntity.ok().build();
    }
}

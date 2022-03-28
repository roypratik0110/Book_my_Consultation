package com.upgrad.userservice.services;

import com.upgrad.userservice.entities.User;
import com.upgrad.userservice.exceptions.RecordNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User register(User user);
    User getUser(String id) throws RecordNotFoundException;
    void uploadDocuments(String userId, MultipartFile file);
}

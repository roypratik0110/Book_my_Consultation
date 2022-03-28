package com.upgrad.userservice.services;

import com.upgrad.userservice.entities.User;
import com.upgrad.userservice.exceptions.RecordNotFoundException;
import com.upgrad.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepo;
    @Autowired
    S3Service s3Service;
    @Autowired
    NotificationService notificationService;

    @Override
    public User register(User user) {
        user.setCreatedDate(LocalDate.now());
        userRepo.save(user);
        notify(user);
        return user;
    }

    @Override
    public User getUser(String id) throws RecordNotFoundException {
        return userRepo.findById(id).orElseThrow(() -> new
                RecordNotFoundException("Requested resource is not available"));
    }

    @Override
    public void uploadDocuments(String userId, MultipartFile file) {
        try {
            s3Service.uploadFileToS3(userId,file);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private void notify(User user){
        notificationService.notifyUser(user);
    }
}

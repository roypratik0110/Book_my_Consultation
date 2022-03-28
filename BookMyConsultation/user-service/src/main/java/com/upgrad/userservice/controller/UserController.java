package com.upgrad.userservice.controller;

import com.upgrad.userservice.dto.UserDTO;
import com.upgrad.userservice.entities.User;
import com.upgrad.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(path = "/users",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO){
        User newUser = modelMapper.map(userDTO,User.class);
        newUser = userService.register(newUser);
        UserDTO newUserDTO = modelMapper.map(newUser,UserDTO.class);
        return new ResponseEntity(newUserDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/users/{id}/documents")
    public ResponseEntity<String> uploadDocuments(@RequestParam("files") MultipartFile[] files, @PathVariable("id") String userId) throws IOException {
        int index=0;
        for(MultipartFile file: files){
            String name = file.getName();
            userService.uploadDocuments(userId,file);
        }
        return ResponseEntity.ok("File(s) uploaded Successfully");
    }

    @PreAuthorize("hasRole('USER','ADMIN')")
    @GetMapping(path = "/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByUserId(@PathVariable(value = "id") String id){
        User retrievedUser = userService.getUser(id);
        UserDTO retrievedUserDTO = modelMapper.map(retrievedUser,UserDTO.class);
        return new ResponseEntity(retrievedUserDTO,HttpStatus.OK);
    }
}

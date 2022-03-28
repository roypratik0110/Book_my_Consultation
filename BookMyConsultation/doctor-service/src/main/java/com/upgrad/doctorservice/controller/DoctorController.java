package com.upgrad.doctorservice.controller;

import com.upgrad.doctorservice.dto.DoctorDTO;
import com.upgrad.doctorservice.entity.Doctor;
import com.upgrad.doctorservice.services.DoctorService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(path = "/doctors",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewDoctor(@Valid @RequestBody DoctorDTO doctorDTO){

        Doctor newDoctor = modelMapper.map(doctorDTO,Doctor.class);
        Doctor addDoctor = doctorService.newDoctor(newDoctor);
        DoctorDTO addDoctorDTO = modelMapper.map(addDoctor, DoctorDTO.class);
        return new ResponseEntity(addDoctorDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/doctors/{id}/documents")
    public ResponseEntity<String> uploadDocuments(@RequestParam("files") MultipartFile[] files, @PathVariable("id") String doctorId) throws IOException {
        int index = 0;
        for (MultipartFile file : files) {
            String name = file.getName();
            doctorService.uploadDocuments(doctorId, file);
        }
        return ResponseEntity.ok("File(s) uploaded Successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/doctors/{id}/approve",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity approveDoctor(@PathVariable("id") String id,@RequestBody DoctorDTO doctorDTO){
        Doctor approvedDoctor = modelMapper.map(doctorDTO,Doctor.class);
        approvedDoctor = doctorService.updateDoctor(id,approvedDoctor,1 );
        DoctorDTO approvedDoctorDTO = modelMapper.map(approvedDoctor,DoctorDTO.class);
        return new ResponseEntity(approvedDoctorDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/doctors/{id}/reject",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity rejectDoctor(@PathVariable("id") String id,@RequestBody DoctorDTO doctorDTO){
        Doctor approvedDoctor = modelMapper.map(doctorDTO,Doctor.class);
        approvedDoctor = doctorService.updateDoctor(id,approvedDoctor,0 );
        DoctorDTO approvedDoctorDTO = modelMapper.map(approvedDoctor,DoctorDTO.class);
        return new ResponseEntity(approvedDoctorDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(path = "/doctors",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity filterDoctor(@RequestParam(value = "status",required = true) String status,
                                             @RequestParam(value = "speciality",required = false) String speciality){
        List<Doctor> allDoctors = doctorService.filterByStatusAndSpeciality(status,speciality);
        List<DoctorDTO> allDoctorsDTO = new ArrayList<>();
        for (Doctor doctor : allDoctors)
            allDoctorsDTO.add(modelMapper.map(doctor,DoctorDTO.class));
        return new ResponseEntity(allDoctorsDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(path = "/doctors/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findDoctorById(@PathVariable(value = "id") String id){
        Doctor gettingDoctor = doctorService.getById(id);
        DoctorDTO gettingDoctorDTO = modelMapper.map(gettingDoctor,DoctorDTO.class);
        return new ResponseEntity(gettingDoctorDTO,HttpStatus.OK);
    }
}

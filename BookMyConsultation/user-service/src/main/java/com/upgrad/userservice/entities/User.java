package com.upgrad.userservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "User")
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String emailId;
    private String mobile;
    private LocalDate createdDate;
}

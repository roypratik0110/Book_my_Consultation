package com.upgrad.userservice.dto;

import com.upgrad.userservice.customValidators.CustomEmailValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String id;
    @NotBlank(message = "first name can not be empty")
    private String firstName;
    @NotBlank(message = "last name can not be empty")
    private String lastName;
    private LocalDate dob;
    @NotBlank
    @CustomEmailValidator
    private String emailId;
    @NotBlank
    @Pattern(regexp = "(^[0-9]{10})",message = "please enter valid mobile no")
    private String mobile;
    private LocalDate createdDate;
}

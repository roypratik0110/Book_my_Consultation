package com.upgrad.userservice.customValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Email(message = "Please provide a valid email")
@Pattern(regexp = ".+@.+\\..+",message = "please provide a valid email")
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface CustomEmailValidator {

    String message() default "Please provide a valid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

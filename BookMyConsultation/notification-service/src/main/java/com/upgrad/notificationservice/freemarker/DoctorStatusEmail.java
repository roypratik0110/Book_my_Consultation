package com.upgrad.notificationservice.freemarker;

import com.upgrad.notificationservice.EmailService;
import com.upgrad.notificationservice.model.Doctor;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DoctorStatusEmail {

    @Autowired
    FreeMarkerConfigurer configurer;
    @Autowired
    EmailService emailService;

    private String approved = "Doctor Registration Approved";
    private String rejected = "Doctor Registration Rejected";

    public void sendMail(Doctor doctor) throws IOException, TemplateException, MessagingException {
        Map<String,Object> templateModel = new HashMap<>();
        templateModel.put("doctor",doctor);
        if (doctor.getStatus().equalsIgnoreCase("Active")){
            Template template = configurer.getConfiguration().getTemplate("doctorapproval.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,templateModel);
            emailService.sendSimpleMessage(doctor.getEmailId(),approved,htmlBody);
        }
        else if (doctor.getStatus().equalsIgnoreCase("Rejected")){
            Template template = configurer.getConfiguration().getTemplate("doctorrejectionl.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,templateModel);
            emailService.sendSimpleMessage(doctor.getEmailId(),rejected,htmlBody);
        }
    }
}

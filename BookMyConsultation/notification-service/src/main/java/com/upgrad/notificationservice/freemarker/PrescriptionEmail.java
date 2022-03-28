package com.upgrad.notificationservice.freemarker;

import com.upgrad.notificationservice.EmailService;
import com.upgrad.notificationservice.model.Prescription;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionEmail {

    @Autowired
    FreeMarkerConfigurer configurer;
    @Autowired
    EmailService emailService;

    private String subject = "Prescription for Appointment";

    public void sendEmail(Prescription prescription) throws TemplateException, IOException, MessagingException {
        Map<String,Object> templateModel = new HashMap<>();
        templateModel.put("prescription",prescription);
        Template template = configurer.getConfiguration().getTemplate("prescription.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,templateModel);
        emailService.sendSimpleMessage(prescription.getEmailId(),subject,htmlBody);
    }
}

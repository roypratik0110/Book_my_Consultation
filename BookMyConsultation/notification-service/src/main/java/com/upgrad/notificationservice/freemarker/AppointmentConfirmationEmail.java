package com.upgrad.notificationservice.freemarker;

import com.upgrad.notificationservice.EmailService;
import com.upgrad.notificationservice.model.Appointment;
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
public class AppointmentConfirmationEmail {

    @Autowired
    FreeMarkerConfigurer configurer;
    @Autowired
    EmailService emailService;

    private String subject = "Appointment Confirmation";

    public void sendEmail(Appointment appointment) throws TemplateException, IOException, MessagingException {
        Map<String,Object> templateModel = new HashMap<>();
        templateModel.put("appointment",appointment);
        Template template = configurer.getConfiguration().getTemplate("appointmentconfirmation.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,templateModel);
        emailService.sendSimpleMessage(appointment.getEmailId(),subject,htmlBody);
    }
}

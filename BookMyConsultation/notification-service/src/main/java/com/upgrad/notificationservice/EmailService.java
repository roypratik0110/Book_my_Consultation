package com.upgrad.notificationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class EmailService {

    private String from = "roypratik1995@gmail.com";
    private String fromName = "Book My Consultation";
    private String accessKey;
    private String secretKey;

    private SesClient sesClient;
    @Value("${spring.mail.host}")
    private String host;

    @PostConstruct
    public void init(){
        accessKey = "AKIAXIAR4W6CKPOJ7ZMQ";
        secretKey = "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";
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(accessKey,secretKey));
        sesClient = SesClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.US_EAST_1)
                .build();
    }

    public void verifyEmail(String emailId){
        sesClient.verifyEmailAddress(req->req.emailAddress(emailId));
    }

    public void sendSimpleMessage(String toEmail,String subject,String body) throws UnsupportedEncodingException, MessagingException {
        Properties prop = System.getProperties();
        prop.put("mail.transport.protocol","smtp");
        prop.put("mail.smtp.port",587);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(prop);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from,fromName));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
        message.setSubject(subject);
        message.setContent(body,"text/html");
        Transport transport = session.getTransport();
        try {
            transport.connect(host,accessKey,secretKey);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Email sent!");
        }catch (Exception e){
            System.out.println("Email was not sent");
            System.out.println(e.getMessage());
        }finally {
            transport.close();
        }
    }
}

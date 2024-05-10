package com.kodat.of.bankproject.service.impl;

import com.kodat.of.bankproject.dto.EmailDetails;
import com.kodat.of.bankproject.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(emailDetails.getRecipient());
            message.setText(emailDetails.getMessageBody());
            message.setSubject(emailDetails.getSubject());
            mailSender.send(message);
            LOGGER.info("email sent successfully");
        } catch (MailException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
    }


package com.kodat.of.bankproject.service.impl;

import com.kodat.of.bankproject.dto.EmailDetails;
import com.kodat.of.bankproject.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

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

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    mimeMessage.setFrom(senderEmail);
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(emailDetails.getAttachment(), file);
            mailSender.send(mimeMessage);
            LOGGER.info("email sent with attachment successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}


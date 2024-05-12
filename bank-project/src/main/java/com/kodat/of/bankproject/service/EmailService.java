package com.kodat.of.bankproject.service;

import com.kodat.of.bankproject.dto.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails) throws MessagingException;
}

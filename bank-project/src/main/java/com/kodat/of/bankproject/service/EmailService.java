package com.kodat.of.bankproject.service;

import com.kodat.of.bankproject.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);

}

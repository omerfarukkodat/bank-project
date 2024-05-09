package com.kodat.of.bankproject.service;

import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.CreditDebitRequest;
import com.kodat.of.bankproject.dto.EnquiryRequest;
import com.kodat.of.bankproject.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
}

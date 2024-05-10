package com.kodat.of.bankproject.service;

import com.kodat.of.bankproject.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);

    void deleteAccount(EnquiryRequest enquiryRequest);
}

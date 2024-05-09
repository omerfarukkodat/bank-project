package com.kodat.of.bankproject.service;

import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
}

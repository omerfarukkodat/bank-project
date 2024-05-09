package com.kodat.of.bankproject.service.impl;

import com.kodat.of.bankproject.dto.AccountInfo;
import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.UserRequest;
import com.kodat.of.bankproject.entity.User;
import com.kodat.of.bankproject.repository.UserRepository;
import com.kodat.of.bankproject.service.UserService;
import com.kodat.of.bankproject.utils.AccountUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        // Creating an account - saving a new user into the db
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            new BankResponse();
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userRequest.getEmail())
                .accountBalance(userRequest.getAccountBalance())
                .phone(userRequest.getPhone())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo
                        .builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getOtherName() + " " + savedUser.getLastName())
                        .build())
                .build();


    }
}

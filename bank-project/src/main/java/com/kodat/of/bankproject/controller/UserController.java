package com.kodat.of.bankproject.controller;

import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.UserRequest;
import com.kodat.of.bankproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

@PostMapping
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.createAccount(userRequest));

    }



}

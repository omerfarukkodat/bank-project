package com.kodat.of.bankproject.controller;

import com.kodat.of.bankproject.dto.*;
import com.kodat.of.bankproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Account Management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create New User Account",
            description = "Creating a new user and assigning an account ID")
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.createAccount(userRequest));
    }
    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number , check how much the user has")
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"
    )
    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> getBalanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(userService.balanceEnquiry(enquiryRequest));

    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(userService.nameEnquiry(enquiryRequest));
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponse> credit(@RequestBody CreditDebitRequest request) {
        return ResponseEntity.ok(userService.creditAccount(request));
    }

    @PostMapping("/debit")
    public ResponseEntity<BankResponse> debit(@RequestBody CreditDebitRequest request) {
        return ResponseEntity.ok(userService.debitAccount(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(userService.transfer(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserAccount(@RequestBody EnquiryRequest enquiryRequest) {
        userService.deleteAccount(enquiryRequest);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);

    }

}

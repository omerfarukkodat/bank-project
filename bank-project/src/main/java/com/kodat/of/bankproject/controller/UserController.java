package com.kodat.of.bankproject.controller;

import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.CreditDebitRequest;
import com.kodat.of.bankproject.dto.EnquiryRequest;
import com.kodat.of.bankproject.dto.UserRequest;
import com.kodat.of.bankproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> getBalanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(userService.balanceEnquiry(enquiryRequest));

    }

@GetMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(userService.nameEnquiry(enquiryRequest));
}

@PostMapping("/credit")
public ResponseEntity<BankResponse> credit(@RequestBody CreditDebitRequest request){
        return ResponseEntity.ok(userService.creditAccount(request));
}

@PostMapping("/debit")
    public ResponseEntity<BankResponse> debit(@RequestBody CreditDebitRequest request){
        return ResponseEntity.ok(userService.debitAccount(request));
}

}

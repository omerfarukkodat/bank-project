package com.kodat.of.bankproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String otherName;
    private String gender;
    private String address;
    private String phone;
    private String alternativePhoneNumber;
    private String stateOfOrigin;
    private BigDecimal accountBalance;
}

package com.kodat.of.bankproject.utils;

import java.time.Year;

public class AccountUtils {
public static String generateAccountNumber() {
    // 2024 + randomSixDigits

    Year currentYear = Year.now();
    int min = 100_000;
    int max = 999_999;

    //generate random number between min and max

    int randomNumber =(int) Math.floor(Math.random() * (max - min + 1) + min);

    //convert the current and randomNumber to strings and concatenate

    String year = String.valueOf(currentYear.getValue());
    String StringRandomNumber = String.valueOf(randomNumber);
    StringBuilder builder = new StringBuilder();

 return builder.append(year).append(StringRandomNumber).toString();

}

public static final String ACCOUNT_EXISTS_CODE = "001";
public static final String ACCOUNT_EXISTS_MESSAGE = "Account already exists";
public static final String ACCOUNT_CREATION_SUCCESS = "002";
public static final String ACCOUNT_CREATION_MESSAGE = "Account creation success";




}

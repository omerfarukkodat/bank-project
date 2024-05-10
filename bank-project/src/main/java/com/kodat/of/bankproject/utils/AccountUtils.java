package com.kodat.of.bankproject.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account already exists";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account creation success";
    public static final String ACCOUNT_NON_EXISTENT_CODE = "003";
    public static final String ACCOUNT_NON_EXISTENT_MESSAGE = "User with the provided Account number does not exist";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_MESSAGE = "User Account redited account success";
    public static final String ACCOUNT_DEBIT_FAIL = "006";
    public static final String ACCOUNT_DEBIT_FAIL_MESSAGE = "User Account debit is more than your current balance";
    public static final String ACCOUNT_DEBIT_SUCCESS = "007";
    public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE = "User Account debit success";
    public static final String SOURCE_ACCOUNT_NOT_EXISTS = "008";
    public static final String SOURCE_ACCOUNT_NOT_EXISTS_MESSAGE= "Source account doesn't exists";
    public static final String DESTINATION_ACCOUNT_NOT_EXISTS = "009";
    public static final String DESTINATION_ACCOUNT_NOT_EXISTS_MESSAGE = "Destination account doesn't exists";
    public static final String TRANSFER_SUCCESS = "010";
    public static final String TRANSFER_SUCCESS_MESSAGE = "Transfer success";



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





}

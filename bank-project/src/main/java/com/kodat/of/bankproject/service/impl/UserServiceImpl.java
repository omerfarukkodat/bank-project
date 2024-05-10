package com.kodat.of.bankproject.service.impl;

import com.kodat.of.bankproject.dto.*;
import com.kodat.of.bankproject.entity.User;
import com.kodat.of.bankproject.repository.UserRepository;
import com.kodat.of.bankproject.service.EmailService;
import com.kodat.of.bankproject.service.TransactionService;
import com.kodat.of.bankproject.service.UserService;
import com.kodat.of.bankproject.utils.AccountUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TransactionService transactionService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.transactionService = transactionService;
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
        // Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your account has been created! \n Your account details: \n" +
                        "Account name: " + savedUser.getFirstName() + " " + savedUser.getOtherName() + " " + savedUser.getLastName()
                        + "\n Account number: " + savedUser.getAccountNumber())
                .build();


        emailService.sendEmailAlert(emailDetails);

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

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check if the provided account number exists in database
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NON_EXISTENT_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NON_EXISTENT_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfo
                        .builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getOtherName() + " " + foundUser.getLastName())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();


    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NON_EXISTENT_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();

    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        // if the account is exists
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NON_EXISTENT_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NON_EXISTENT_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);

        //Save transaction

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDTO);



        return BankResponse
                .builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .accountInfo(AccountInfo
                        .builder()
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getOtherName() + " " + userToCredit.getLastName())
                        .build())
                .build();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        //check if the account exists
        // check if the amount you intend to withdraw is not more than current account balance
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NON_EXISTENT_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NON_EXISTENT_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigDecimal debitAmount = request.getAmount();
        if (userToDebit.getAccountBalance().compareTo(debitAmount) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_FAIL)
                    .responseMessage(AccountUtils.ACCOUNT_DEBIT_FAIL_MESSAGE)
                    .accountInfo(AccountInfo
                            .builder()
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(userToDebit.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getOtherName() + " " + userToDebit.getLastName())
                            .build())
                    .build();
        }
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(userToDebit);

        //Save transaction

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(userToDebit.getAccountNumber())
                .transactionType("DEBIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDTO);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBIT_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_DEBIT_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo
                        .builder()
                        .accountBalance(userToDebit.getAccountBalance())
                        .accountNumber(userToDebit.getAccountNumber())
                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getOtherName() + " " + userToDebit.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        // get the account to debit (check if exists)
        //check if the amount debit is not more than current balance
        // debit the account
        //get the account to credit
        //credit the account

        boolean isSourceAccountExists = userRepository.existsByAccountNumber(request.getSourceAccountNumber());
        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if (!isDestinationAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTS)
                    .responseMessage(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        if (!isSourceAccountExists) {
            return BankResponse
                    .builder()
                    .responseCode(AccountUtils.SOURCE_ACCOUNT_NOT_EXISTS)
                    .responseMessage(AccountUtils.SOURCE_ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToSource = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User userToDestination = userRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if (request.getAmount().compareTo(userToSource.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_FAIL)
                    .responseMessage(AccountUtils.ACCOUNT_DEBIT_FAIL_MESSAGE)
                    .accountInfo(AccountInfo
                            .builder()
                            .accountBalance(userToSource.getAccountBalance())
                            .accountNumber(userToSource.getAccountNumber())
                            .accountName(userToSource.getFirstName() + " " + userToSource.getOtherName() + " " + userToSource.getLastName())
                            .build())
                    .build();
        }
        userToSource.setAccountBalance(userToSource.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(userToSource);
        EmailDetails debitAlert = EmailDetails
                .builder()
                .recipient(userToSource.getEmail())
                .subject("DEBIT MESSAGE")
                .messageBody("The sum of " + request.getAmount() + " has been deducted from your account!" + userToSource.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);


        userToDestination.setAccountBalance(userToDestination.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToDestination);

        EmailDetails creditAlert = EmailDetails
                .builder()
                .recipient(userToDestination.getEmail())
                .subject("DEBIT MESSAGE")
                .messageBody("The sum of " + request.getAmount() + " has been sent to from your account!" + userToDestination.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        //Save transaction

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(userToDestination.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDTO);

        return BankResponse
                .builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESS)
                .responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(null)
                .build();


    }

    @Override
    public void deleteAccount(EnquiryRequest enquiryRequest) {
       User user = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
       if (user != null){
           userRepository.delete(user);
       }else{
           throw new RuntimeException("user not found");
       }






    }}

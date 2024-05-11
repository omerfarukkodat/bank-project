package com.kodat.of.bankproject.controller;

import com.kodat.of.bankproject.entity.Transaction;
import com.kodat.of.bankproject.service.impl.BankStatement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {

    private final BankStatement bankStatement;


    public TransactionController(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }
    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) {

        return bankStatement.generateStatement(accountNumber, startDate, endDate);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTransaction(@RequestParam String transactionId) {

         bankStatement.deleteTransaction(transactionId);
        return ResponseEntity.ok("transaction deleted successfully");
    }


}

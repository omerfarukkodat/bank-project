package com.kodat.of.bankproject.repository;

import com.kodat.of.bankproject.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    void deleteByTransactionId(String transactionId);
    Transaction findByAccountNumber(String accountNumber);


}

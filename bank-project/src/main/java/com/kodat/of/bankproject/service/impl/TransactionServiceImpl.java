package com.kodat.of.bankproject.service.impl;

import com.kodat.of.bankproject.dto.TransactionDTO;
import com.kodat.of.bankproject.entity.Status;
import com.kodat.of.bankproject.entity.Transaction;
import com.kodat.of.bankproject.repository.TransactionRepository;
import com.kodat.of.bankproject.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDTO.getTransactionType())
                .accountNumber(transactionDTO.getAccountNumber())
                .amount(transactionDTO.getAmount())
                .status(Status.SUCCESS)
                .build();
        transactionRepository.save(transaction);
        LOG.info("Transaction saved successfully");

    }
}

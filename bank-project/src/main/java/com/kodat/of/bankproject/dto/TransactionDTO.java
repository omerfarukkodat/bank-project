package com.kodat.of.bankproject.dto;

import com.kodat.of.bankproject.entity.Status;
import lombok.*;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private Status status;
}

package com.kodat.of.bankproject.repository;

import com.kodat.of.bankproject.dto.BankResponse;
import com.kodat.of.bankproject.dto.CreditDebitRequest;
import com.kodat.of.bankproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findByAccountNumber(String accountNumber);

}

package com.kodat.of.bankproject.repository;

import com.kodat.of.bankproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

}

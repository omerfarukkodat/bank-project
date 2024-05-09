package com.kodat.of.bankproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String otherName;
    private String gender;
    private String address;
    private String phone;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String alternativePhoneNumber;
  @CreationTimestamp
   private LocalDateTime createdAt;
   @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private String status;
    private String stateOfOrigin;

}

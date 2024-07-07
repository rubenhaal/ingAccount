package com.example.ing.IngAccount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String identifier;
    private AccountType accountType;
    private boolean temporaryAccount;
    private LocalDate closureDate;
    private BigDecimal deposit;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proprietary_id", referencedColumnName = "id")
    private Person proprietary;
}

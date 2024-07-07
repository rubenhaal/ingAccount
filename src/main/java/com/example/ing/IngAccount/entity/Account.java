package com.example.ing.IngAccount.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(min = 35, max = 35)
    private String identifier;
    @NotNull
    private AccountType accountType;
    private boolean temporaryAccount;
    private LocalDate closureDate;
    @NotNull
    private BigDecimal deposit;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proprietary_id", referencedColumnName = "id")
    private Person proprietary;
}

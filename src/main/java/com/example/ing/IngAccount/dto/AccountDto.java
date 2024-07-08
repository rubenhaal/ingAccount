package com.example.ing.IngAccount.dto;

import com.example.ing.IngAccount.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountDto {

    @Size(min = 35, max = 35)
    private String identifier;
    @NotNull
    private AccountType accountType;
    private boolean temporaryAccount;
    private LocalDate openingDate;
    private LocalDate closureDate;
    @NotNull
    private BigDecimal deposit;
    @NotNull
    private PersonDto proprietary;

}

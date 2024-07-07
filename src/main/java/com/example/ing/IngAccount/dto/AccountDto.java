package com.example.ing.IngAccount.dto;

import com.example.ing.IngAccount.entity.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountDto {

    private String identifier;
    private AccountType accountType;
    private boolean temporaryAccount;
    private LocalDate closureDate;
    private BigDecimal deposit;
    private PersonDto person;

}

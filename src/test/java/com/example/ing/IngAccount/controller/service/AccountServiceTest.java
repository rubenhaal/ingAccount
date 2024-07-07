package com.example.ing.IngAccount.controller.service;

import com.example.ing.IngAccount.controller.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;


    @Test
    void shouldReturnAnAccountant_whenAnIdentifierIsRetrieve(){

        Account accountById = accountService.findAccountById(UUID.randomUUID());
        assertNotNull(accountById);
    }

}
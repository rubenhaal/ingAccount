package com.example.ing.IngAccount.service;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;


    @Test
    void shouldReturnAnAccountant_whenAnIdentifierIsRetrieve(){
        //given
        when(accountRepository.findByIdentifier(any())).thenReturn(new Account());

        //When
        Account accountById = accountService.findAccountById("test");

        //then
        assertNotNull(accountById);
    }

}
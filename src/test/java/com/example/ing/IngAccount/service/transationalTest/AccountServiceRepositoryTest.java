package com.example.ing.IngAccount.service.transationalTest;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.repository.AccountRepository;
import com.example.ing.IngAccount.service.AccountService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class AccountServiceRepositoryTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;



    @BeforeEach
    void setup(){
        accountRepository.deleteAll();
        accountRepository.flush();

        Account account = new Account();
        account.setIdentifier("identifier");
        accountRepository.save(account);

    }

    @Test
    void shouldReturnAnAccount_whenFindByIdIsCalled(){

        Account account = accountService.findAccountById("identifier");

        assertNotNull(account);
    }

    @Test
    void shouldNotFindAnAccount_whenFindByIdIsCalled(){

        Account account = accountService.findAccountById("teset");

        assertNull(account);
    }

}

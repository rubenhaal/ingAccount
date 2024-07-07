package com.example.ing.IngAccount.service.transationalTest;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.entity.Person;
import com.example.ing.IngAccount.repository.AccountRepository;
import com.example.ing.IngAccount.service.AccountService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.ing.IngAccount.entity.AccountType.CURRENT;
import static org.junit.jupiter.api.Assertions.*;

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

        Account account = createPermanentAccount("acc1");
        accountRepository.save(account);

    }

    @Test
    void shouldReturnAnAccount_whenFindByIdIsCalled(){

        Account account = accountService.findAccountById("acc1");

        assertNotNull(account);
        assertFalse(account.isTemporaryAccount());
        assertEquals(CURRENT, account.getAccountType());
        assertNotNull(account.getProprietary());
        assertEquals("firstName",account.getProprietary().getFirstName());
    }

    @Test
    void shouldNotFindAnAccount_whenFindByIdIsCalled(){

        Account account = accountService.findAccountById("test");
        assertNull(account);
    }

    private Account createPermanentAccount(String id){
        return Account.builder()
                .identifier(id)
                .accountType(CURRENT)
                .temporaryAccount(false)
                .deposit(BigDecimal.valueOf(1000))
                .proprietary(Person.builder()
                        .firstName("firstName")
                        .lastName("lastName")
                        .dateOfBirth(LocalDate.now())
                        .email("test@test.com")
                        .build())
                .build();
    }

}

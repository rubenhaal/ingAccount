package com.example.ing.IngAccount.integration;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.entity.Person;
import com.example.ing.IngAccount.repository.AccountRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.ing.IngAccount.entity.AccountType.CURRENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @LocalServerPort
    private int port;



    @BeforeEach
    void setup(){
        accountRepository.deleteAll();
        accountRepository.flush();

        Account account = createPermanentAccount("acc1");
        accountRepository.save(account);
    }

    @Test
    @SneakyThrows
    void shouldReturnAccount_whenGetCallIsPerform(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/getAllAccounts";
        ResponseEntity<Account[]> response = testRestTemplate.getForEntity(url, Account[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar el producto devuelto
        Account[] account = response.getBody();
        assertNotNull(account);
        assertEquals("acc1", account[0].getIdentifier());
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

package com.example.ing.IngAccount.integration;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.dto.PersonDto;
import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.entity.AccountType;
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
import java.util.Optional;

import static com.example.ing.IngAccount.entity.AccountType.CURRENT;
import static org.junit.jupiter.api.Assertions.*;

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

        Account account1 = createPermanentAccount("acc2");
        accountRepository.save(account1);
    }

    @Test
    @SneakyThrows
    void shouldReturnAccountList_whenGetCallIsPerform(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/getAllAccounts";
        ResponseEntity<AccountDto[]> response = testRestTemplate.getForEntity(url, AccountDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto[] account = response.getBody();
        assertNotNull(account);
        assertEquals("acc1", account[0].getIdentifier());
    }

    @Test
    @SneakyThrows
    void shouldReturnAnAccount_whenGetByIdIsCalled(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";
        ResponseEntity<AccountDto> response = testRestTemplate.getForEntity(url, AccountDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto account = response.getBody();
        assertNotNull(account);
        assertEquals("acc2", account.getIdentifier());
        PersonDto person = account.getProprietary();
        assertNotNull(person);
        assertEquals("firstName",person.getFirstName());
    }

    @Test
    void shouldCreateEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/createAccount";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);
        accountDto.setIdentifier("TestIdentifier");

        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity(url, accountDto, AccountDto.class);

        AccountDto accountResponse = response.getBody();
        assertNotNull(accountResponse);
        assertEquals("TestIdentifier", accountResponse.getIdentifier());
        assertEquals(AccountType.SAVINGS, accountResponse.getAccountType());

        assertNotNull(accountRepository.findByIdentifier("TestIdentifier"));
    }


    @Test
    void shouldNotCreateEntity_whenAccountNotValidDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/createAccount";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);
        accountDto.setIdentifier("TestIdentifier");

        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity(url, accountDto, AccountDto.class);

        AccountDto accountResponse = response.getBody();
        assertNotNull(accountResponse);
        assertEquals("TestIdentifier", accountResponse.getIdentifier());
        assertEquals(AccountType.SAVINGS, accountResponse.getAccountType());

        assertNotNull(accountRepository.findByIdentifier("TestIdentifier"));
    }

    @Test
    void shouldUpdateEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);

        testRestTemplate.put(url, accountDto);
        //then
        Optional<Account> account2Opt = accountRepository.findByIdentifier("acc2");

        assertTrue(account2Opt.isPresent());
        assertEquals(AccountType.SAVINGS, account2Opt.get().getAccountType());

    }


    @Test
    void shouldDeleteEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";
        testRestTemplate.delete(url);

        Optional<Account> account2Opt = accountRepository.findByIdentifier("acc2");

        assertTrue(account2Opt.isEmpty());
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

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
    void shouldCreateAccountEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/createAccount";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);

        accountDto.setOpeningDate(LocalDate.now());
        accountDto.setTemporaryAccount(false);
        accountDto.setDeposit(BigDecimal.valueOf(100));
        accountDto.setOpeningDate(LocalDate.now());

        PersonDto personDto = new PersonDto();
        personDto.setEmail("test@email.com");
        personDto.setFirstName("Testuser");
        personDto.setLastName("testlastname");
        personDto.setDateOfBirth(LocalDate.of(2000,1,10));

        accountDto.setProprietary(personDto);

        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity(url, accountDto, AccountDto.class);

        AccountDto accountResponse = response.getBody();
        assertNotNull(accountResponse);
        assertNotNull(accountResponse.getIdentifier());
        assertEquals(AccountType.SAVINGS, accountResponse.getAccountType());

        assertNotNull(accountRepository.findByIdentifier(accountResponse.getIdentifier()));
    }


    @Test
    void shouldCreateTemporaryAccountEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/createAccount";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);

        accountDto.setOpeningDate(LocalDate.now());
        accountDto.setTemporaryAccount(true);
        accountDto.setDeposit(BigDecimal.valueOf(100));
        accountDto.setOpeningDate(LocalDate.now());
        accountDto.setClosureDate(LocalDate.now().plusMonths(3));

        PersonDto personDto = new PersonDto();
        personDto.setEmail("test@email.com");
        personDto.setFirstName("Testuser");
        personDto.setLastName("testlastname");
        personDto.setDateOfBirth(LocalDate.of(2000,1,10));

        accountDto.setProprietary(personDto);

        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity(url, accountDto, AccountDto.class);

        AccountDto accountResponse = response.getBody();
        assertNotNull(accountResponse);
        assertNotNull(accountResponse.getIdentifier());
        assertEquals(AccountType.SAVINGS, accountResponse.getAccountType());

        assertNotNull(accountRepository.findByIdentifier(accountResponse.getIdentifier()));
    }

    @Test
    void shouldNotCreateEntity_whenAccountNotValidDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/createAccount";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);
        accountDto.setIdentifier("TestIdentifier");

        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, accountDto, String.class);
        assertTrue(accountRepository.findByIdentifier("TestIdentifier").isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldUpdateEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";

        AccountDto accountDto = new AccountDto();

        accountDto.setOpeningDate(LocalDate.now());
        accountDto.setTemporaryAccount(false);
        accountDto.setDeposit(BigDecimal.valueOf(100));
        accountDto.setOpeningDate(LocalDate.now());

        testRestTemplate.patchForObject(url, accountDto, AccountDto.class);
        //then
        Optional<Account> account2Opt = accountRepository.findByIdentifier("acc2");

        assertTrue(account2Opt.isPresent());
        assertTrue(account2Opt.get().getDeposit().compareTo(BigDecimal.valueOf(100))==0);
        assertFalse(account2Opt.get().isTemporaryAccount());

    }

    @Test
    void shouldUpdateTemporalAccountEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";

        AccountDto accountDto = new AccountDto();

        accountDto.setOpeningDate(LocalDate.now());
        accountDto.setTemporaryAccount(true);
        accountDto.setClosureDate(LocalDate.now().plusMonths(1).plusDays(15));
        accountDto.setDeposit(BigDecimal.valueOf(100));
        accountDto.setOpeningDate(LocalDate.now());

        testRestTemplate.patchForObject(url, accountDto, AccountDto.class);
        //then
        Optional<Account> account2Opt = accountRepository.findByIdentifier("acc2");

        assertTrue(account2Opt.isPresent());
        assertTrue(account2Opt.get().getDeposit().compareTo(BigDecimal.valueOf(100))==0);
        assertTrue(account2Opt.get().isTemporaryAccount());

    }

    @Test
    void shouldNotUpdateEntity_whenAccountDtoIsReceived(){
        String urlHost= "http://localhost:"+port;

        String url = urlHost+"/api/account/acc2";

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.SAVINGS);


        testRestTemplate.patchForObject(url, accountDto, String.class);

        Optional<Account> account2Opt = accountRepository.findByIdentifier("acc2");

        assertTrue(account2Opt.isPresent());
        assertEquals(CURRENT, account2Opt.get().getAccountType());

        testRestTemplate.put(url, accountDto);
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

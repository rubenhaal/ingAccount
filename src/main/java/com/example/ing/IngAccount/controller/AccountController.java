package com.example.ing.IngAccount.controller;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    //Not required just for testing purpose
    @GetMapping(value = "/getAllAccounts")
    public List<AccountDto> getAllAccounts(){
        return accountService.findAllAccounts();
    }

    @GetMapping(value = "{identifier}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String identifier){
        return accountService.findAccountById(identifier)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/createAccount")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){

        AccountDto response = accountService.createNewAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

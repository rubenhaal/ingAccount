package com.example.ing.IngAccount.controller;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

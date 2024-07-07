package com.example.ing.IngAccount.controller;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<Account> getAllAccounts(){
        return accountService.findAllAccounts();
    }
}

package com.example.ing.IngAccount.service;

import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountById(String uuid){
        return accountRepository.findByIdentifier(uuid);
    }
}

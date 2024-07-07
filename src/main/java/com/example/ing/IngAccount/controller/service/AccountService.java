package com.example.ing.IngAccount.controller.service;

import com.example.ing.IngAccount.controller.entity.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {


    public Account findAccountById(UUID uuid){
        return new Account();
    }
}

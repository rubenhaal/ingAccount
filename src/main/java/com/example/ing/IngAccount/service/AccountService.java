package com.example.ing.IngAccount.service;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.mapper.AccountMapper;
import com.example.ing.IngAccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    private final AccountMapper accountMapper = AccountMapper.INSTANCE;

    public List<AccountDto> findAllAccounts(){
        return accountRepository.findAll().stream().map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AccountDto> findAccountById(String uuid){
        return accountRepository.findByIdentifier(uuid)
                .map(accountMapper::toDto);
    }
}

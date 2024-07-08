package com.example.ing.IngAccount.service;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.entity.Account;
import com.example.ing.IngAccount.mapper.AccountMapper;
import com.example.ing.IngAccount.mapper.PersonMapper;
import com.example.ing.IngAccount.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    private final AccountMapper accountMapper = AccountMapper.INSTANCE;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public List<AccountDto> findAllAccounts(){
        return accountRepository.findAll().stream().map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AccountDto> findAccountById(String uuid){
        return accountRepository.findByIdentifier(uuid)
                .map(accountMapper::toDto);
    }

    public AccountDto createNewAccount(AccountDto accountDto){
        Account account = accountMapper.toEntity(accountDto);
        account.setIdentifier(UUID.randomUUID().toString());
        return accountMapper.toDto(accountRepository.save(account));
    }

    public AccountDto updateAccount(String identifier, AccountDto accountDto){

        Account account = accountRepository.findByIdentifier(identifier).orElseThrow();

        account.setTemporaryAccount(accountDto.isTemporaryAccount());
        account.setAccountType(accountDto.getAccountType());
        account.setDeposit(accountDto.getDeposit());
        account.setProprietary(personMapper.toEntity(accountDto.getProprietary()));
        account.setClosureDate(accountDto.getClosureDate());

        account = accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    public void deleteAccount(String identifier){
        accountRepository.deleteByIdentifier(identifier);
    }
}

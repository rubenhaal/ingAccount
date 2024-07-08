package com.example.ing.IngAccount.controller;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.exception.CreatingErrorException;
import com.example.ing.IngAccount.service.AccountService;
import com.example.ing.IngAccount.service.ValidatorService;
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
    @Autowired
    private ValidatorService validatorService;

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

        List<String> errors = validatorService.validateAccount(accountDto);

        if(!errors.isEmpty()){
            throw new CreatingErrorException(errors);
        }
//        if (!errors.isEmpty()) {
//            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//        }

        AccountDto response = accountService.createNewAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{identifier}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String identifier, @RequestBody AccountDto accountDto){
        AccountDto accountResponse = accountService.updateAccount(identifier, accountDto);
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("{identifier}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String identifier){
        accountService.deleteAccount(identifier);
        return ResponseEntity.noContent().build();
    }
}

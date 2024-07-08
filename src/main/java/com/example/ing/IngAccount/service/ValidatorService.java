package com.example.ing.IngAccount.service;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@Service
public class ValidatorService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public static final int UUID_LENGTH = 36;

    public List<String> validateAccount(AccountDto accountDto){
        List<String> errors = new ArrayList<>();

        if(!checkOpeningAccount(accountDto.getOpeningDate())){
            errors.add("Opening Date should be at least 30 days");
        }

        if(accountDto.getAccountType()==null){
            errors.add("On creation Account type is mandatory");
        }

        if(!checkClosureDate(accountDto.getClosureDate(), accountDto.isTemporaryAccount())){
            errors.add("Temporary Account should have closure date");
        }
        if(!checkInitialDeposit(accountDto.getDeposit())){
            errors.add("Not negative deposite are allowed");
        }

        if(accountDto.getProprietary()==null){
            errors.add("Account should have a propietary");
        }else{
            errors.addAll(validatePerson(accountDto.getProprietary()));
        }

        return errors;
    }

    public List<String> validateUpdateAccount(AccountDto accountDto){
        List<String> errors = new ArrayList<>();

        if(!checkOpeningAccount(accountDto.getOpeningDate())){
            errors.add("Opening Date should be at least 30 days");
        }
        if(accountDto.getAccountType()!=null){
            errors.add("Account type can not be changed");
        }

        if(!checkClosureDateUpdate(accountDto.getClosureDate(), accountDto.isTemporaryAccount())){
            errors.add("Temporary Account should have closure date");
        }
        if(!checkInitialDeposit(accountDto.getDeposit())){
            errors.add("Not negative deposite are allowed");
        }

        if(accountDto.getProprietary()!=null){
            errors.addAll(validatePerson(accountDto.getProprietary()));
        }

        return errors;
    }

    public List<String> validatePerson(PersonDto personDto){
        List<String> errors = new ArrayList<>();
        if(!checkName(personDto.getFirstName())){
            errors.add("First Name should be larger than 3 chars and shorter than 35 chars");
        }
        if(!checkName(personDto.getLastName())){
            errors.add("Last Name should be larger than 3 chars and shorter than 35 chars");
        }
        if(!checkMail(personDto.getEmail())){
            errors.add("Not valid email");
        }
        if(!checkAge(personDto.getDateOfBirth())){
            errors.add("the user should have at least 18 years old ");
        }
        return errors;
    }

    private boolean checkName(String name){

        return name !=null && name.length() > 3 && name.length() <= 35;

    }
    private boolean checkMail(String email){
        return EMAIL_PATTERN.matcher(email).matches();
    }
    private boolean checkAge(LocalDate birthDate){
        return birthDate!=null &&Period.between(birthDate, LocalDate.now()).getYears()>=18;
    }


    private boolean checkOpeningAccount(LocalDate openingAccount){
        return openingAccount!= null &&Period.between(openingAccount, LocalDate.now()).getDays()<=30;
    }

    private boolean checkClosureDate(LocalDate localDate, boolean temporary){

        if (temporary){
            return localDate!=null && Period.between(LocalDate.now(), localDate).getMonths()>=2;
        }
        return true;

    }

    private boolean checkClosureDateUpdate(LocalDate localDate, boolean temporary){

        if (temporary){
            return localDate!=null && Period.between(LocalDate.now(), localDate).getMonths()>=1;
        }
        return true;

    }

    private boolean checkInitialDeposit(BigDecimal deposit){
        return deposit!=null && deposit.compareTo(BigDecimal.ZERO)>=0;
    }
}

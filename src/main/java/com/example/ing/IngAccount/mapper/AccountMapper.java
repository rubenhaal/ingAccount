package com.example.ing.IngAccount.mapper;

import com.example.ing.IngAccount.dto.AccountDto;
import com.example.ing.IngAccount.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}

package com.example.ing.IngAccount.mapper;

import com.example.ing.IngAccount.dto.PersonDto;
import com.example.ing.IngAccount.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
}

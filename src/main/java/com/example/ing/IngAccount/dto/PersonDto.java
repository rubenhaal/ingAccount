package com.example.ing.IngAccount.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;

}

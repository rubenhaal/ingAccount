package com.example.ing.IngAccount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;

//    @OneToMany(mappedBy = "proprietary", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Account> accounts;
    @OneToOne(mappedBy = "proprietary", cascade = CascadeType.ALL)
    private Account account;

}

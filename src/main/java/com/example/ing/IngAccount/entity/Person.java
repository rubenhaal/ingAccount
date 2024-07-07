package com.example.ing.IngAccount.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @Size(min = 3, max = 35)
    private String firstName;
    @Size(min = 3, max = 35)
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @Email
    private String email;

    @OneToOne(mappedBy = "proprietary", cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private Account account;

}

package com.example.ing.IngAccount.repository;

import com.example.ing.IngAccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdentifier(String identifier);
}

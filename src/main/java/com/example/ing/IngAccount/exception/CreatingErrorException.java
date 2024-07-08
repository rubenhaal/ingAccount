package com.example.ing.IngAccount.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CreatingErrorException extends RuntimeException{
    private final List<String> errors;

    public CreatingErrorException(List<String> errors) {
        this.errors = errors;
    }
}

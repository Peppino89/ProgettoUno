package com.example.progettouno.exception;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String message) {

        super(message);
    }
}

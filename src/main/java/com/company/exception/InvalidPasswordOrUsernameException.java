package com.company.exception;

public class InvalidPasswordOrUsernameException extends RuntimeException {
    public InvalidPasswordOrUsernameException(String message) {
        super("Invalid password or username!");
    }
}

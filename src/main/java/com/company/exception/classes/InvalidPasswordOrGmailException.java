package com.company.exception.classes;

public class InvalidPasswordOrGmailException extends RuntimeException {
    public InvalidPasswordOrGmailException() {
        super("Invalid password or gmail");
    }
}

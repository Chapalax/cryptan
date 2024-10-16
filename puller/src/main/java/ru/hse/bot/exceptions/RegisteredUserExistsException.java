package ru.hse.bot.exceptions;

public class RegisteredUserExistsException extends RuntimeException {
    public RegisteredUserExistsException(String message) {
        super(message);
    }
}

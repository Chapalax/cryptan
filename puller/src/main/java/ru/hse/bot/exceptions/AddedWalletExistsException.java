package ru.hse.bot.exceptions;

public class AddedWalletExistsException extends RuntimeException {
    public AddedWalletExistsException(String message) {
        super(message);
    }
}

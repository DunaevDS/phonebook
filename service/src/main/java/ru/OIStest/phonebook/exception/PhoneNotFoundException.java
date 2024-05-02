package ru.OIStest.phonebook.exception;

public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException(String message) {
        super(message);
    }
}

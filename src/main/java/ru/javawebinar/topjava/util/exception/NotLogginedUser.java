package ru.javawebinar.topjava.util.exception;

public class NotLogginedUser extends RuntimeException {
    public NotLogginedUser(String message) {
        super(message);
    }
}

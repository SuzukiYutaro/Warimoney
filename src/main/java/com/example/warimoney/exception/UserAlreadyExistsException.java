package com.example.warimoney.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("ユーザ名は既に使用されています: " + username);
    }
}

package com.system.watchCar.service;

public class UserExecption extends RuntimeException{

    private String msg;

    public UserExecption(String message) {
        super(message);
        msg = message;
    }

    public UserExecption(String message, Exception exception) {
        msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

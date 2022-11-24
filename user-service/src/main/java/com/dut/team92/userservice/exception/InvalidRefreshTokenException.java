package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonAuthException;

public class InvalidRefreshTokenException extends CommonAuthException {

    private static InvalidRefreshTokenException instance;

    private InvalidRefreshTokenException() {
        super(401, "Invalid refresh token");
    }

    public static InvalidRefreshTokenException getInstance() {
        if (instance == null)
            synchronized (InvalidRefreshTokenException.class) {
                instance = new InvalidRefreshTokenException();
            }
        return instance;
    }

}

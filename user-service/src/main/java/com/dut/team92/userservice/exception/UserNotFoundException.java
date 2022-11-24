package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonAuthException;

public class UserNotFoundException extends CommonAuthException {

    public UserNotFoundException(String message) {
        super(400, message);
    }
}

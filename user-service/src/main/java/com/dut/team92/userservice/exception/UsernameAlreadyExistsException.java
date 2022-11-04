package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class UsernameAlreadyExistsException extends CommonBadRequestException {
    public UsernameAlreadyExistsException() {
        super(400, "Username already exists!");
    }
}

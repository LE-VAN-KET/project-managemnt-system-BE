package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class EmailAlreadyExistsException extends CommonBadRequestException {
    public EmailAlreadyExistsException() {
        super(400, "Email already exists!");
    }
    public EmailAlreadyExistsException(String email) {
        super(400, "Email already exists with " + email);
    }
}

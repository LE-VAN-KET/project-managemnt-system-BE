package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class UserNotFoundException extends CommonBadRequestException {
    public UserNotFoundException(String message) {
        super(400, message);
    }
}

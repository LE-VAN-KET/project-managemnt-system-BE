package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonAuthException;

public class UserNotEnabledException extends CommonAuthException {
    public UserNotEnabledException() {
        super(401, "User is not enabled");
    }
}

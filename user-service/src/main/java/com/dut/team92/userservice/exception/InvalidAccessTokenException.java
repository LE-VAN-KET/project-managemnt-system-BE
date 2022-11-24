package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonAuthException;

public class InvalidAccessTokenException extends CommonAuthException {
    public InvalidAccessTokenException() {
        super(401, "Invalid access token");
    }
}

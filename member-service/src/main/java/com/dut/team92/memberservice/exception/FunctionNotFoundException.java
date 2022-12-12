package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class FunctionNotFoundException extends CommonBadRequestException {
    public FunctionNotFoundException(String message) {
        super(400, message);
    }
}

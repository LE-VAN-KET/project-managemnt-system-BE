package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class RoleExistException extends CommonBadRequestException {
    public RoleExistException(String message) {
        super(400, message);
    }
}

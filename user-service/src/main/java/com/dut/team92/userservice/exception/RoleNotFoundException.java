package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class RoleNotFoundException extends CommonBadRequestException {
    public RoleNotFoundException(String message) {
        super(400, message);
    }
}

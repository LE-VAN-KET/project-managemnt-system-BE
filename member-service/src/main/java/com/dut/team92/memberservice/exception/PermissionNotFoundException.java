package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class PermissionNotFoundException extends CommonBadRequestException {
    public PermissionNotFoundException(String message) {
        super(400, message);
    }
}

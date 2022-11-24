package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class BadRequestExceptionOrganization extends CommonBadRequestException {
    public BadRequestExceptionOrganization(Integer code, String message) {
        super(code, message);
    }
}

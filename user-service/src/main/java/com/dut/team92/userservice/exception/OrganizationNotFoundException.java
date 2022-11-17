package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class OrganizationNotFoundException extends CommonBadRequestException {
    public OrganizationNotFoundException(String message) {
        super(400, message);
    }
}

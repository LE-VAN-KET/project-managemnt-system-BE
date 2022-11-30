package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class SprintNotFoundException extends CommonBadRequestException {
    public SprintNotFoundException(String message) {
        super(400, message);
    }
}

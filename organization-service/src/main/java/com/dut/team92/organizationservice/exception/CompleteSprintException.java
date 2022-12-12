package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class CompleteSprintException extends CommonBadRequestException {
    public CompleteSprintException(String message) {
        super(400, message);
    }
}

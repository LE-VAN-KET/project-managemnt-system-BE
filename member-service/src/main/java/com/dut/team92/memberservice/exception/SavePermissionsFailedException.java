package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class SavePermissionsFailedException extends CommonBadRequestException {
    public SavePermissionsFailedException(String message) {
        super(400, message);
    }
}

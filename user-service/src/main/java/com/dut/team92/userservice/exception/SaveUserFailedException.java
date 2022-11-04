package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class SaveUserFailedException extends CommonBadRequestException {
    public SaveUserFailedException() {
        super(400, "Could not save user");
    }
}

package com.dut.team92.userservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class FailedReadDataFileCSV extends CommonBadRequestException {
    public FailedReadDataFileCSV(String message) {
        super(500, message);
    }
}

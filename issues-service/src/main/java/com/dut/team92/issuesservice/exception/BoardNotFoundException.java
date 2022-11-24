package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class BoardNotFoundException extends CommonBadRequestException {
    public BoardNotFoundException(String message) {
        super(400, message);
    }
}

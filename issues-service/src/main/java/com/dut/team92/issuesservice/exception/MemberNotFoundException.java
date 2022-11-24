package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class MemberNotFoundException extends CommonBadRequestException {
    public MemberNotFoundException(String message) {
        super(400, message);
    }
}

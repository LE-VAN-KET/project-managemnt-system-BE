package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class IssuesNotFoundException extends CommonBadRequestException {
    public IssuesNotFoundException(String message) {
        super(400, message);
    }
}

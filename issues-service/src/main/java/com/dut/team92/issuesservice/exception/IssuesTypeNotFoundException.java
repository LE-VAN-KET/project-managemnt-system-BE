package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class IssuesTypeNotFoundException extends CommonBadRequestException {
    public IssuesTypeNotFoundException(String message) {
        super(400, message);
    }
}

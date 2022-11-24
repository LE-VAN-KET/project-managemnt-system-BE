package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class IssuesStatusNotFoundException extends CommonBadRequestException {
    public IssuesStatusNotFoundException(String message) {
        super(400, message);
    }
}

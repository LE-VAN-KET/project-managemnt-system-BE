package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class ProjectKeyNotFoundException extends CommonBadRequestException {
    public ProjectKeyNotFoundException(String message) {
        super(400, message);
    }
}

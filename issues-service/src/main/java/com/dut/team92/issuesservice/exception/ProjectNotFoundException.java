package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class ProjectNotFoundException extends CommonBadRequestException {
    public ProjectNotFoundException(String message) {
        super(400, message);
    }
}

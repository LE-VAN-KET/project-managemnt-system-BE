package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class ProjectIdNotFound extends CommonBadRequestException {
    public ProjectIdNotFound(String message) {
        super(400, message);
    }
}

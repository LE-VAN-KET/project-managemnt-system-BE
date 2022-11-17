package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonNotFoundException;

public class OrganizationIdNotFound extends CommonNotFoundException {
    public OrganizationIdNotFound(Integer code, String message) {
        super(code, message);
    }
}

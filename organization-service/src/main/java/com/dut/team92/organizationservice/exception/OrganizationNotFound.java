package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonNotFoundException;

public class OrganizationNotFound extends CommonNotFoundException {
    public OrganizationNotFound(Integer code, String message) {
        super(code, message);
    }
}

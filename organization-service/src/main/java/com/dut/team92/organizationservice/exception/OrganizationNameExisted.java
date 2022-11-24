package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class OrganizationNameExisted extends CommonBadRequestException {
    public OrganizationNameExisted(Integer code, String message) {
        super(code, message);
    }
}

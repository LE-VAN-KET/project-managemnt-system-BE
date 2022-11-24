package com.dut.team92.organizationservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class ProjectDomainExisted extends CommonBadRequestException {
    public ProjectDomainExisted(Integer code, String message) {
        super(code, message);
    }
}

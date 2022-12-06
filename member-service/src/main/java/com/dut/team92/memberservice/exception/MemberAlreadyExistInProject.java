package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonBadRequestException;

public class MemberAlreadyExistInProject extends CommonBadRequestException {
    public MemberAlreadyExistInProject(String message) {
        super(400, message);
    }
}

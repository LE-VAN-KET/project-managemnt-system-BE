package com.dut.team92.memberservice.exception;

import com.dut.team92.common.exception.CommonNotFoundException;

public class MemberNotFoundException extends CommonNotFoundException {
    public MemberNotFoundException(Integer code, String message) {
        super(code, message);
    }
}

package com.dut.team92.issuesservice.exception;

import com.dut.team92.common.exception.CommonServerErrorException;

public class SaveIssuesFailedException extends CommonServerErrorException {
    public SaveIssuesFailedException(String message) {
        super(500, message);
    }
}

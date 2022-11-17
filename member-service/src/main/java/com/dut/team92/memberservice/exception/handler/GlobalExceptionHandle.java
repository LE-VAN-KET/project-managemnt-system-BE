package com.dut.team92.memberservice.exception.handler;

import com.dut.team92.common.exception.handler.CommonExceptionHandler;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.memberservice.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle extends CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoleNotFoundException.class)
    public CommonErrorResponse handleRoleNotFoundException(RoleNotFoundException exception) {
        return handleBadRequestException(exception, "RoleNotFoundException");
    }
}
